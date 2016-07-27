# kps

##Arhitektura same aplikacije

- Aplikacija je podignuta na **Spring Boot** frameworku. Dodani su starteri za mongoDB. log4j 2, spring integration, web services i web dio. Spring boot je izabran zbog jednostavnosti konfiguracije. 
- Baza je MongoDB. Izabrana je zbog brzine simultanog pristupa podacima, dobre podrške i velikog community-ja
[DB Rankings](http://db-engines.com/en/ranking). Zbog jednostavnosti samih upita moguće je "iskopčati" boot starter za mongodb i dodati
npr. cassandru uz manje preinake. Isto tako, ukoliko se koristi relacijska baza, kod također nije problematično prilagoditi, jer se koristi apstrakcijski data sloj za pristup (j)dbc driverima.Impementacija jednog, drugog i/ili trećeg rješenja ovisi o konkretnom slučaju (kompleksnost podatkovnog modela, broj read/write operacija). Trenutni model zadovoljava mongoDb baza i što se tiče brzine, replikacije i naknadnog izvlačenja analitičkih/sintetičkih podataka.
- Spring integration je izabran zbog pojednostavljivanja kompleksnih operacija integracije različitih sustava u jednu kompaktnu cjelinu. Putem enterprise integration pattern-a je moguće puno jednostavnije i čišće rješiti standardne probleme koji se pojavljuju u relativno kompleksnim sustavima kao što je ovaj.
- Logging framework je log4j2 - izabran je zbog velikog broja mogućih logging adaptera + ima puno bolju non-blocking arhitekture.
- Build je na mavenu. Gradle je možda čistiji i "next best thing", ali maven ima još uvijek veći community i bolju podršku
- Deploy je na Embedded Tomcat 7 instanci koja je upakirana u fat JAR pa se tako može jednostavno startati kao stand alone aplikacija lokalno ili u cloud-u
- dodane su još neke specifičnosti u pom i u strukturu projekta kako bi se aplikacija mogla deploy-ati na openshift cloud platformi (post commit i startup/stop hook-ovi)

##Pojašnjenje funkcionalnosti
- ws endpoint klase su generirane iz dobivenih wsdl-ova, ali kako sam napisao u mailu, promijenio sam nazive objekata/endpoint-a zbog testiranja
- podignut je jedan mock controller na koji idu svi requestovi sa provisioning endpoint-ova
- podignut je jedan endpoint koji prihvaća soap ws requestove koji su dodjeljeni klijentu

##Integracijski dio
Ovaj dio je centralna točka koja vodi računa o svim djelovima sustava.
Htio sam dodati ovdje i integration graph, ali ga spring sts nije najsretnije prikazao, pa bi više odmagao nego pomagao

##Flow

- Klijent šalje jedan od mogućih requestova na podignuti endpoint koji  je u ovom slučaju **inboundGateway** koji prima poruke sa **wsRequestChannel-a**. On ih transformira u objekte preko un/marshallera i prosljeđuje dalje
- poruke idu na **payload-type-router** koji određuje popayloadu poruke koja će se funkcionalnost odraditi
1. **status**  
  - Ako se zatraži status request-a, router prosljeđuje poruku na service-activator koji u bazi gleda u kojem je stanju request za koji se traži stanje i pretvori objekt u adekvatni response
  - Status se vraća natrag na **wsRequestChannel-a** i putem ws-a vraća klijentu(preko **outputExceptionCheckerRouterChannel** koji provjerava da li je greška u pitanju i pretvara response u onaj definiran u xsd schemi).
2. **poništavanje**
  - poruka ide na service activator koji poziva servis koji postavlja status u ERROR (ako je status iz baze bio u PENDING) te na injektirani **controlBusChannel** šalje komandu za prekid rada na elementima **http-outbound-gateway,asyncExecutorProcessData,provisioningAggregator**
  - **control-bus** odradi svoju magiju (ili bi barem trebao odraditi)
  -  vraća se poruka natrag na **wsRequestChannel-a** i putem ws-a vraća klijentu (preko **outputExceptionCheckerRouterChannel** u pitanju i pretvara response u onaj definiran u xsd schemi).
3. **provision subscriber-a**
  - poruka se prosljeđuje na **service-activator** koji zapisuje request u bazu kao log
  - poruka ide dalje prem **recipient-list-router**-u koji šalje request na 2 lokacije: instantni odgovor klijentskom sustavu sa request id-om i nastavak rada na objavi rute za datog subscribera na provisioning sustavima
  - jedna poruka se vraća na istom principu kao i u prva dva slučaja
  - druga poruka ide preko **processChannel** kanala koji ovaj proces starta asinkrono preko **asyncExecutor**-a
  - poruka dolazi na **splitter** koji puruku šalje na svaki od provisioning sustava
  - u ovom elementu [u ovoj klasi](https://github.com/aportolan/kps/blob/master/kps/src/main/java/hr/aportolan/kps/service/impl/ProvisionSplitterServiceImpl.java) se odrađuje dio splittanja poruke na više sustava.
  Ovdje se vrši provjera ruta (lokalna?, parent?), dodaju se headeri (lokacije provisioning sustava, metode PUT,DELETE,GET,POST)  log svakog odlaznog request-a zasebno.
  -  poruka ide dalje na **http-outbound-gateway** koji ima funkciju da te poruke putem http protokola prosljeđuje na provisioning sustave. U ovisnosti o headerima poruka se mijenjaju url-ovi i metode
  - **http-outbound-gateway** ima u sebi **request-handler-advice-chain** koji preko **retryAdvice** radi retransmisiju failure poruka (sve dok ga nešto ne prekine, ili, što je vjerojatnije u ovom slučaju, dok se ne sruši sustav :) ) -**retryAdvice** je definiran [ovdje](https://github.com/aportolan/kps/blob/master/kps/src/main/resources/retry-config.xml) i inkludan u integracijski xml.
  - Ako je sve prošlo kako treba, poruke bi se trebale vratiti na **provisioningAggregator** koj skuplja sve response-ove i kada su svi stigli (tj. kada je ispunjen uvijet release strategije = koliko provisioning requestova je otišlo, toliko ih se mora i vratiti - taj podatak imam kao count u bazi po requestu) po correlation id-u, radi se slanje notifikacija na kanal koji ide prema izlaznom gateway-u
  - **marshallingGateway** radi soap request i obavještava klijentov soap endpoint da je operacija završila

 
 
