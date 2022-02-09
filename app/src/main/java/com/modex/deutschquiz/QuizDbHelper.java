package com.modex.deutschquiz;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quizShqip.db";
    public static final int DATABASE_VERSION = 1;


    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;



        final String SQL_CREATE_QUESTIONS_TABLE = " CREATE TABLE " +
                QuestionContractClass.QuestionsTable.TABLE_NAME + " ( " +
                QuestionContractClass.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                QuestionContractClass.QuestionsTable.COLUMN_QUESTIONS + " TEXT , " +
                QuestionContractClass.QuestionsTable.COLUMN_OPTION1 + " TEXT , " +
                QuestionContractClass.QuestionsTable.COLUMN_OPTION2 + " TEXT , " +
                QuestionContractClass.QuestionsTable.COLUMN_OPTION3 + " TEXT , " +
                QuestionContractClass.QuestionsTable.COLUMN_OPTION4 + " TEXT , " +
                QuestionContractClass.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER ," +
                QuestionContractClass.QuestionsTable.COLUMN_DIFFICULTY + " TEXT " +
                " ) ";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + QuestionContractClass.QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // make it look simple and not complicated

//    Here WE can add questions !!!



    private void fillQuestionsTable () {

        //Easy FRIDAY 0
        QuestionClass q1 = new QuestionClass("Studie über den Ursprung der Worte", "Ethologie", "Antropognia", "Paläanthropologie", "Ethiologie", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q1);
        QuestionClass q2 = new QuestionClass("10. auf Albanisch", "Ten", "Dhjete", "Tien", "Dek",  2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q2);
        QuestionClass q3 = new QuestionClass("Schöpfer des Periodensystems", "John Dalton", "Ernest Rutherford", "Dmitry Mendeleev", "Alfred Nobel", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q3);
        QuestionClass q4 = new QuestionClass("Element, das im Lang bei Raumtemperatur aktiviert wird", "Wasserstoff", "Zink", "Aluminium",
                "Mercury", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q4);

        //EASY FRIDAY 1

        QuestionClass q5 = new QuestionClass("In welchem Land finden Sie den Kilimandscharo?", "Tansania", "Chile", "Paraguay", "Uruguay", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q5);
        QuestionClass q6 = new QuestionClass("Eine extreme Übertreibung der Wörter", "Metapher", "Übertreibung", "Epithet", "Idioma", 2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q6);
        QuestionClass q7 = new QuestionClass("CAD", "Computer Architecture Design", "Computer Analyzing Design", "Computer Aided Design", "Computer Assistant Design", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q7);
        QuestionClass q8 = new QuestionClass("Dies ist die Zahlenreihe, in der die ersten beiden Terme 0 und 1 sind und jede Folgende Zahl die Summe zweier Vorfahren ist.", "Arithmetische Sequenz", "Cauchy-Sequenz", "Periodische Sequenz", "Fibonac-Sequenz", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q8);

        //EASY FRIDAY 2
        QuestionClass q9 = new QuestionClass("Kleinster Staat der Welt", "Vatikan", "Monaco", "San Marino", "Tuvalu", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q9);
        QuestionClass q10 = new QuestionClass("Die größte Insel der Welt", "Madagaskar", "Grönland", "Neuguinea", "Borneo", 2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q10);
        QuestionClass q11 = new QuestionClass("Gründer von drei Grundgesetzen der Bewegung", "Galileo Galilei", "Isaac Borrow", "Isaac Newton", "Johan Kepler", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q11);
        QuestionClass q12 = new QuestionClass("Aus welchem Staat fließt das Wort Chemie", "Indien", "China", "Persien", "Ägypten", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q12);

        //EASY FRIDAY 3
        QuestionClass q13 = new QuestionClass("Shakespeare-Soldat Ausländer Held", "Otelli", "Hamlet", "Prinz Hal", "Anthony", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q13);
        QuestionClass q14 = new QuestionClass("Erster Präsident in Kumanovo", "Ramiz Alia", "Ahmet Zogu", "Sali Berisha", "Asnje", 2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q14);
        QuestionClass q15 = new QuestionClass("Eisen im Periodensystem", "I", "HE", "FE", "GD", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q15);
        QuestionClass q16 = new QuestionClass("Studium der Felsen", "Liturgie", "Limnologie", "Labeorphilie", "Litologie", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q16);

        //EASY Monday 4
        // TODO: 1/11/2021 * today we will still implement some question on our db and check it from online questions or do it ourselves. make sure they are correct
        QuestionClass q17 = new QuestionClass("10 Erste Zahlen von Pi-Se", "3.1415926535", " 3.1415929535", " 3.1415926534", " 3.1419526535", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q17);
        QuestionClass q18 = new QuestionClass("Wasserstoff-Entdecker", "Amedeo Avogadro", "Henry Cavendish", "John Dalton", "Antoine Lavoisie", 2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q18);
        QuestionClass q19 = new QuestionClass("Welcher Staat heißt \"Staat der Seen\"", "Schweden", "Norwegen", "Finnland", "Russland", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q19);
        QuestionClass q20 = new QuestionClass("Was ist die relative Molekülmasse der Amonachie", "4", "7","16","17", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q20);

        //Easy Monday 5
        QuestionClass q21 = new QuestionClass("Was ist der Staat mit der größten Einwohnerdichte", "China", "Indien", "Russland", "Brasilien", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q21);
        QuestionClass q22 = new QuestionClass("In welchem Jahr reiste der Magelan über die Welt hinaus", "1418", "1419", "1420", "1421", 2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q22);
        QuestionClass q23 = new QuestionClass("Was hat Methenia das Wort \"ISKENDER\"", "Vella", "Empire Ranger", "Alexander", "Warrior", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q23);
        QuestionClass q24 = new QuestionClass("Saint Patrick's Day", "17. März", "17. April", "17. Mai", "17. Juni", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q24);

        //EASY MONDAY 6
        QuestionClass q25 = new QuestionClass("Eine Verbindung aus Wasserstoff und Kohlenstoffatomen", "Kohlenwasserstoff", "Wasserstoff", "Helium", "Holmium", 1, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q25);
        QuestionClass q26 = new QuestionClass("Wir stellvertretender Führer, der in Futurama erschienen ist", "Mike Pence", "Al Gore", "Joe Biden", "Dick Cheney", 2, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q26);
        QuestionClass q27 = new QuestionClass("Schwarze Magie und ist ein spiritueller Glaube, der Magie und Magie verwendet.", "Inkatation", "Chanium", "Juju", "Asnje", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q27);
        QuestionClass q28 = new QuestionClass("Welches Metall ist bei Raumtemperatur nicht fest?", "Zink", "Barium", "Indium", "Quecksilber", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q28);

        // EASY MONDAY EXTRA +2
        QuestionClass q29 = new QuestionClass("Hyperaktivitätsstörung durch Aufmerksamkeitsdefizit", "Depression", "Angst", "ADHAND", "Semundje de Turrete", 3, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q29);
        QuestionClass q30 = new QuestionClass("Russischer Herrscher", "Putin", "Stalin", "Malenkow", "Zar", 4, QuestionClass.DIFFICULTY_EASY);
        AddQuestion(q30);

        // TODO: 1/11/2021 Start with medium 30 questions
        //MEDIUM MONDAY 0
        QuestionClass q31 = new QuestionClass("Ish arma tregtare e peshkimit", "Harpoon", "Fishing 300", "AK Fishnet", "Royal Nets", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q31);
        QuestionClass q32 = new QuestionClass("Artisti i natës me yje", "Leonardo Da Vinci", "Vincent Van Gogh", "Jan van Eyck", "Gustav Klimt", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q32);
        QuestionClass q33 = new QuestionClass("Viti i krijimit te Magna Kartes", "1213", "1214", "1215", "1216", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q33);
        QuestionClass q34 = new QuestionClass("Kanadezi i parë që ecën në hapësirë", "Yuri Gagarin", "Alan Shepard","Indium","Chris Hadfield", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q34);

        //Medium Monday 1
        QuestionClass q35 = new QuestionClass("Ku u ndërtua Titaniku", "Belfast", "London", "Leeds", "Bristol", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q35);
        QuestionClass q36 = new QuestionClass("Viti i katastroges i Çernobilit", "1985" ,"1986", "1987", "1988", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q36);
        QuestionClass q37 = new QuestionClass("Renia e murit te Berlinit", "1987", "1988", "1989", "1990", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q37);
        QuestionClass q38 = new QuestionClass("Beteja e Britanise", "1937", "1938","1939","1940", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q38);

        // Medium Monday 2
        QuestionClass q39 = new QuestionClass("Kur u vendos monedha Euro", "1999", "2000", "2001", "2002", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q39);
        QuestionClass q40 = new QuestionClass("metoda e luftës ku sulmuesi kryeson një sulm duke përdorur një përqendrim të shpejtë të forcës dërrmuese që mund të përbëhet nga forca të blinduara dhe të motorizuara ose të mekanizuara të këmbësorisë",
                "Beetle" ,"Blitzkrieg", "Turtle", "British Broom", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q40);
        QuestionClass q41 = new QuestionClass("Astronauti amerikan që ka qëndruar më gjatë në orbitë", "Scott Kelly", "Christina Koch", "Peggy Whitson", "Neil Armstrong", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q41);
        QuestionClass q42 = new QuestionClass("Graphics Interchangeable Format", "1937", "Graphics Index Figure","Graphics Index Format","Graphics Interchange Format", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q42);

        //MEDIUM MONDAY 3
        QuestionClass q43 = new QuestionClass("50 ne numra romake", "L", "X", "XX", "C", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q43);
        QuestionClass q44 = new QuestionClass("Trajtimi me rrezet x", "chemotherapy" ,"radiotherapy", "brachytherapy", "radiation oncology", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q44);
        QuestionClass q45 = new QuestionClass("Ministri i parë i Skocisë", "Rupert Murdoch", "Sadiq Khan", "Nicola sturgeon", "Tony Blair", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q45);
        QuestionClass q46 = new QuestionClass("Kush e zbuloi penicilinën", "Marie Curie", "John Dalton","JJ Thomson","Alexander Fleming", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q46);

        // Medium MONDAY 4
        QuestionClass q47 = new QuestionClass("një kënd më i madh se 180", "i lugët", "i myset", "kend-drejte", "kend-ngushte", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q47);
        QuestionClass q48 = new QuestionClass("çdo kënd më pak se 180", "i lugët", "i myset", "kend-drejte", "kend-ngushte", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q48);
        QuestionClass q49 = new QuestionClass("Sa deputete perbehet parlamenti i Shqiperise", "160", "150", "140", "130", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q49);
        QuestionClass q50 = new QuestionClass("Monedha ne Korene e Veriut", "Yen", "Baht","Dram","Won", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q50);

        //Medium Monday 5
        QuestionClass q51 = new QuestionClass("Te vdesesh nga e qeshura", "Chrysippus", "Cardiomyopathy", "Congenital", "Coronary", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q51);
        QuestionClass q52 = new QuestionClass("Trofta, barrakuda dhe krapi janë të gjitha llojet e çfarë?", "Gjitare", "Peshk", "Amfib","Gjitarët detarë", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q52);
        QuestionClass q53 = new QuestionClass("Si quhet shkopi Australian që mund të kthehet te hedhësi i tij?","Hedhës shtizash", "Hedhes shkopi", "Boomerang", "Boomering", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q53);
        QuestionClass q54 = new QuestionClass("Sa pjesë ka një lojtar në Ludo?", "1", "2","3","4", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q54);

        // Medium Monday 6

        QuestionClass q55 = new QuestionClass("Sa lojtarë ka atje në një ekip rugby", "15", "17", "20", "23", 1, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q55);
        QuestionClass q56 = new QuestionClass("Ping-pongu është një emër alternativ për cilin sport?", "Tenis", "Tabel Tenis", "Tenis Pong","Asnje", 2, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q56);
        QuestionClass q57 = new QuestionClass("Cila është prona më e shtrenjtë në një bord standard monopoli?","Mayfair", "Trafalgar Square", "Boardwalk", "BOND STREET", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q57);
        QuestionClass q58 = new QuestionClass("Cila fanellë me ngjyra fitohet nga lideri në Tour de France?", "Bardhe", "Blu","Kuqe","Jeshile", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q58);

        // Medium Monday EXTRA +2

        QuestionClass q59 = new QuestionClass("Nofka e qytetit të New York-ut","Time Square", "Wall Street", "Big Apple", "The Empire State", 3, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q59);
        QuestionClass q60 = new QuestionClass("Shpikësi i Nintendo Wii", "Minoru Arakawa", "Tei Yamauchi","Fusajiro Yamauchi","Kashi Kabushiki", 4, QuestionClass.DIFFICULTY_MEDIUM);
        AddQuestion(q60);


        // TODO: 1/11/2021 Wednesday Hard 0
        QuestionClass q61 = new QuestionClass("Autostrada më e gjatë në botë", "Trans-Canada", "katërkëndëshit të artë, Indi", "Autostrada Kombëtare e Kinës 010", "Linja amerikane 20", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q61);
        QuestionClass q62 = new QuestionClass("Gjatësia e autostradës më të gjatë në botë", "7000 km", "8000 km", "9000 km","10000 km", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q62);
        QuestionClass q63 = new QuestionClass("Vend që përbën gati një të tretën e prodhimit të përgjithshëm të drurit në botë","Kina", "Etiopia", "India", "Nigeria", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q63);
        QuestionClass q64 = new QuestionClass("Vend i njohur si \"Vendi i bakrit\"", "Cape Town", "Bejing","Kongo","Zambia", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q64);

        // Wednesday HARD 1
        QuestionClass q65 = new QuestionClass("Lumi Volga derdhet në", "Deti Kaspik", "Deti i Zi", "Gjiri Persik", "Deti i Kuq", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q65);
        QuestionClass q66 = new QuestionClass("Vendi më i ftohtë në Tokë", "Anadyr ne Siberi", "Verkoyansk ne Siberi", "Novosibirsk ne Siberi","Tobolsk ne Siberi", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q66);
        QuestionClass q67 = new QuestionClass("Vend i cili renditet i dyti për nga sipërfaqja e tokës","Kina", "Rusia", "Kanada", "Shtetet e Bashkuara te Amerikes", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q67);
        QuestionClass q68 = new QuestionClass("Ishulli më i madh në detin Mesdhe", "Lampedusa", "Pantelleria","Malta","Sicili", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q68);

        // Wednesday Hard 2

        QuestionClass q69 = new QuestionClass("Lumi Jordan derdhet në", "Deti i vdekur", "Deti i Kuq", "Deti Arabik", "Deti i Kuq", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q69);
        QuestionClass q70 = new QuestionClass("Kryeqyteti që qëndron në lumin Danub", "Sarajevo", "Beograd", "Zagreb","Graz", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q70);
        QuestionClass q71 = new QuestionClass("Gjatësia e kanalit anglez","562 km", "563 km", "564 km", "565 km", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q71);
        QuestionClass q72 = new QuestionClass("Qyteti më i vjetër i njohur në botë", "Jericho", "Plovdiv","Gaziantep","Damascus", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q72);

        //Wednesday Hard 3
        QuestionClass q73 = new QuestionClass("Qyteti i Kanaleve", "Venecia", "Amsterdam", "Stockholm", "Annecys", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q73);
        QuestionClass q74 = new QuestionClass("Vend në të cilin rrjedh lumi Wangchu", "Torsha", "Myanmar", "Singimari","Jaldhaka", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q74);
        QuestionClass q75 = new QuestionClass("Qyteti i cili është qendra më e madhe për prodhimin e automobilave në botë","Munich", "Frankfurt", "Detroit", "Mannheim", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q75);
        QuestionClass q76 = new QuestionClass("Vendi i cili është prodhuesi më i madh i gomës në botë", "Japonia", "India","Kina","Malaysia", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q76);

        //WEDNESDAY HARD 4
        QuestionClass q77 = new QuestionClass("Në fizikë, puna përcaktohet si","forca x distanca", "forca / distanca", "distance / koha", "forca x koha", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q77);
        QuestionClass q78 = new QuestionClass("Nëse ngrini dy ngarkesa një kat, sa punë bëni në krahasim me ngritjen vetëm të një ngarkese në një kati?", "Gjysem", "Dy her me shume", "Kater her me shume","Nuk Ndryshon", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q78);
        QuestionClass q79 = new QuestionClass("Njësia e punës është","Watt", "Newton", "Joule", "Koha", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q79);
        QuestionClass q80 = new QuestionClass("Sasia e energjisë potenciale që posedon një objekt i ngritur është e barabartë me", "vlera e nxitimit për shkak të gravitetit.", "Fuqia e përdorur për ngritjen e saj.","distanca që ngrihet.","puna e bërë në ngritjen e saj.", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q80);

        //Wednesday Hard 5
        QuestionClass q81 = new QuestionClass("Energjia kinetike e një objekti është e barabartë me", "gjysma e produktit të masës", "masa e saj u shumëfishua", "masa u pjestua ", "masa u shumezua me kohen", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q81);
        QuestionClass q82 = new QuestionClass("Një shigjetë në një hark ka 70 J energji të mundshme. Duke supozuar se nuk ka humbje të energjisë në nxehtësi, sa energji kinetike do të ketë pasi të jetë qëlluar?", "60 J", "70 J", "80 J","90 J", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q82);
        QuestionClass q83 = new QuestionClass("Kur shpejtësia e një makine trefishohet, energjia e saj kinetike","E njejte", "rritet me 3 herë", "rritet me 9 herë", "rritet me 21 herë", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q83);
        QuestionClass q84 = new QuestionClass("Raketat lëshohen nga një aeroplan në drejtimin përpara të lëvizjes. Energjia kinetike e aeroplanit do të jetë", "E njejte", "Rritur","Ne Perpjestim te drejte me masen e rraketes","Ulur", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q84);

        // Wednesday Hard 6
        QuestionClass q85 = new QuestionClass("Cila kërkon më shumë punë: ngritja e një thesi 70 kg vertikalisht 2 metra apo ngritja e një thesi 35 kg vertikalisht 4 metra?", "Te dyja", "Thesi 70 Kg", "Thesi 35 Kg", "Asnje", 1, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q85);
        QuestionClass q86 = new QuestionClass("Një top hidhet në ajër me 100 J energji kinetike, e cila shndërrohet në energji potenciale gravitacionale në majë të trajektores së saj. Kur të kthehet në nivelin e tij origjinal pasi të ketë hasur në rezistencën e ajrit, energjia e saj kinetike është", "> 100 J", "< 100 J", "= 100 J","<= 100 J", 2, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q86);
        QuestionClass q87 = new QuestionClass("Një objekt që ka energji kinetike duhet të jetë","Ne pozicion fillestar", "Ne Renie", "Duke Levizur", "Duke u Ngritur", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q87);
        QuestionClass q88 = new QuestionClass("Merrni parasysh molekulat e gazit hidrogjen dhe molekulat e gazit më të rëndë të oksigjenit që kanë të njëjtën energji kinetike. Molekulat me shpejtësi më të madhe janë", "Asnje", "Te dyja shpejtesi te njejte","Oksigjeni","Hidrogjeni", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q88);

        // Wednesday Hard +2
        QuestionClass q89 = new QuestionClass("Sa xhule pune bëhen në një kuti kur një forcë prej 25 dhe e shtyn atë 3 m?","15 J ", "45 J ", "75 J", "95 J", 3, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q89);
        QuestionClass q90 = new QuestionClass("Sa energji kërkohet për të bërë 40 J punë në një objekt në 5 sekonda?", "0", "2","4","8", 4, QuestionClass.DIFFICULTY_HARD);
        AddQuestion(q90);


        // END OF QUESTIONS




















    }

    private void AddQuestion(QuestionClass questionsClass) {

        ContentValues cv = new ContentValues();
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_QUESTIONS, questionsClass.getQuestion());
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_OPTION1, questionsClass.getOption1());
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_OPTION2, questionsClass.getOption2());
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_OPTION3, questionsClass.getOption3());
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_OPTION4, questionsClass.getOption4());
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_ANSWER_NR, questionsClass.getAnswerNr());
        cv.put(QuestionContractClass.QuestionsTable.COLUMN_DIFFICULTY, questionsClass.getDifficulty());
        db.insert(QuestionContractClass.QuestionsTable.TABLE_NAME, null, cv);

    }




    @SuppressLint("Range")
    public ArrayList<QuestionClass>getQuestions(String difficulty) {
        ArrayList<QuestionClass> questionsClassesList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};


        Cursor cursor = db.rawQuery(" SELECT * FROM " + QuestionContractClass.QuestionsTable.TABLE_NAME + " WHERE " +
                QuestionContractClass.QuestionsTable.COLUMN_DIFFICULTY + " = ? ",selectionArgs );


        if (cursor.moveToFirst()) {
            do {

                QuestionClass questionsClass = new QuestionClass();
                questionsClass.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_QUESTIONS)));
                questionsClass.setOption1(cursor.getString(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_OPTION1)));
                questionsClass.setOption2(cursor.getString(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_OPTION2)));
                questionsClass.setOption3(cursor.getString(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_OPTION3)));
                questionsClass.setOption4(cursor.getString(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_OPTION4)));
                questionsClass.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_ANSWER_NR)));
                questionsClass.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionContractClass.QuestionsTable.COLUMN_DIFFICULTY)));
                questionsClassesList.add(questionsClass);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questionsClassesList;
    }
}