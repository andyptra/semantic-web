/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfunction;

import com.hp.hpl.jena.ontology.OntModel;
//import java.io.InputStream;
//import org.topbraid.spin.arq.ARQ2SPIN;
import org.topbraid.spin.arq.ARQFactory;
//import org.topbraid.spin.model.Select;
//import org.topbraid.spin.system.SPINModuleRegistry;

//import org.topbraid.spin.constraints.ConstraintViolation;
//import org.topbraid.spin.constraints.SPINConstraints;
import org.topbraid.spin.inference.SPINInferences;
//import org.topbraid.spin.system.SPINLabels;
import org.topbraid.spin.system.SPINModuleRegistry;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.ontology.OntModelSpec;
//import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.ReificationStyle;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.io.InputStream ;

/**
 *
 * @author lulu
 */
public class Library {

    public static OntModel ontModel = null;
    public static com.hp.hpl.jena.query.QueryExecution qexec = null;
    public static Model wordModel = null;
    public static String stopWord = "";
    public static String questionWord = "";
    static String theClass = "";
    static String theClassMeaning = "";
    static String test = "";
    static String lastProperty = "";
    public static long timeNeeded = 0;
    static String countQuery = "";   // create query to count the data found
    public static ArrayList<String> pattern = new ArrayList<String>();
    public static ArrayList<String> sentence = new ArrayList<String>();
    public static ArrayList<String> sword = new ArrayList<String>();
    public static ArrayList<String> meaning = new ArrayList<String>();
    public static String aPattern[][];

//    public static HashMap<String, ArrayList<String>> sentence = new HashMap<String, ArrayList<String>>();
//    public static HashMap<String, String> sentence = new HashMap<String, String>();
    public static Model getOntology(String ontFile) {
        Model baseModel = ModelFactory.createDefaultModel(ReificationStyle.Minimal);
        baseModel.read(ontFile);
        return baseModel;
    }

    public static void getWordModel() {
        wordModel = ModelFactory.createDefaultModel(ReificationStyle.Minimal);
        wordModel.read("http://localhost/words.owl");
    }

    //  public  getInferenceOntology() {
    public static void getInferenceOntology() {
        Model baseModel;
        baseModel = getOntology("http://localhost/ethnomed.owl");
//               OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, baseModel);
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, baseModel);

        if (ontModel == null) {
            System.out.println("<h1>NULL</h1>");
        } else {
            System.out.println("<h1>ADA</h1>");

        }

        // Create and add Model for inferred triples
        Model newTriples = ModelFactory.createDefaultModel(ReificationStyle.Minimal);
        ontModel.addSubModel(newTriples);

        // Register locally defined functions
        SPINModuleRegistry.get().registerAll(ontModel, null);

        // Run all inferences
        SPINInferences.run(ontModel, newTriples, null, null, false, null);
    }

    public static void start() { // mulai eksekusi
        SPINModuleRegistry.get().init();
        getWordModel();
        getInferenceOntology();
        createStopWord();
        createQuestionWord();
    }

    public static ResultSet runQuery(String query) {
        ResultSet rs = null;
        Query arqQuery = ARQFactory.get().createQuery(ontModel, query);
        qexec = ARQFactory.get().createQueryExecution(arqQuery, ontModel);
        try {
            rs = qexec.execSelect();
        } finally {
//            qexec.close();
        }
        return rs;
    }

//    public static ResultSet runQuery(String query) { // mulai query ethnomed
//        ResultSet rs = null;
//        Query arqQuery = ARQFactory.get().createQuery(ontModel, query);
//        qexec = ARQFactory.get().createQueryExecution(arqQuery, ontModel);
//        try {
//            rs = qexec.execSelect();
//        } finally {
////            qexec.close();
//        }
//        return rs;
//    }
    public static ResultSet runQueryWord(String query) { // mulai query words
        ResultSet rs = null;
        Query arqQuery = ARQFactory.get().createQuery(wordModel, query);
        qexec = ARQFactory.get().createQueryExecution(arqQuery, wordModel);
        try {
            rs = qexec.execSelect();
        } finally {
//            qexec.close();
        }
        return rs;
    }

    public static void createStopWord() {  // stopword
        stopWord = " ";
        String query = ""
                + "PREFIX words:<http://example.org/words#>"
                + "select ?a "
                + " where { "
                + " ?s words:stopword ?a . "
                + " } ";

        ResultSet rs = runQueryWord(query); // Filtering
        if (rs != null) {
            for (; rs.hasNext();) {
                QuerySolution qs = rs.nextSolution();
                stopWord += qs.getLiteral("a").getValue().toString() + " ";
            }
        }
        qexec.close();

    }

    public static void createQuestionWord() {
        questionWord = " ";
        String query = ""
                + "PREFIX words: <http://example.org/words#>"
                + "select ?a "
                + " where { "
                + " ?s words:questionword ?a . "
                + " } ";

        // Filtering
        ResultSet rs = runQueryWord(query);
        if (rs != null) {
            for (; rs.hasNext();) {
                QuerySolution qs = rs.nextSolution();
                questionWord += qs.getLiteral("a").getValue().toString() + " ";
            }
        }
        qexec.close();

    }

    public static void parsing(String _word, int pos) { // proses representasi kalimat
        //- check the word to keyword synonim
        Stemmer strstem = new Stemmer();

        String wordStem = strstem.stem(_word).trim();
        String _sentence = "";
        String _meaning = "";
        String res = "";
//        if(pos==1){
//            wordStem =  wordStem.replaceAll("bagian","bagiantanaman");
//        }           
        String query
                = "PREFIX words: <http://example.org/words#>"
                + "SELECT DISTINCT ?c ?fc ?w ?m ?sy  "
                + "WHERE {"
                + "?k <http://example.org/words#category> ?c ."
                + "?k <http://example.org/words#forclass> ?fc ."
                + "?k <http://example.org/words#word> ?w ."
                + "?k <http://example.org/words#meaning> ?m ."
                + "?k <http://example.org/words#synonim> ?sy ."
                + "FILTER(  ?sy ='" + wordStem + "' ";
        if (pos == 1) {
            query += " && ?c='class' ";
        } else {
            query += " && ?c!='class' && (?fc = '" + Library.theClass + "' || ?fc = 'all' )";
        }

        query += " )} ";

        Library.test += query + "<br/><br/>";

        ResultSet rs = runQueryWord(query);             // run query word
        if (rs != null) {

            int i = 0;
            while (rs.hasNext()) {
                //- if there is a result, its mean the word is not a value
                i++;
                QuerySolution qs = rs.nextSolution();
                _sentence = qs.getLiteral("w").getValue().toString();
                _meaning = qs.getLiteral("m").getValue().toString();

                // last item in arraylist compare to this result
                if (!sentence.isEmpty() && (sentence.get(sentence.size() - 1).compareTo(_sentence) == 0)) {
                    //- if its same with previous, ignore it
                    meaning.set(sentence.size() - 1, _meaning);
                    continue;
                }
                pattern.add(qs.getLiteral("c").getValue().toString());
                sentence.add(_sentence);
                sword.add(wordStem);
                meaning.add(_meaning);

                if (qs.getLiteral("c").getValue().toString().trim().equalsIgnoreCase("properti")) {
                    lastProperty = _sentence;
                }
                if (pos == 1) {
                    Library.theClass = _sentence;
//                    Library.theClassMeaning = _meaning;
                    //meaning.add(_word+" yang ");
                } else {
//                    meaning.add(_word);
                }
            }
            if (i == 0) {
                if (wordStem.equalsIgnoreCase("dan") || wordStem.equalsIgnoreCase("atau")) {
                    pattern.add("properti");
                    sentence.add(lastProperty);
                    sword.add(lastProperty);
                    meaning.add(" ");
                }
                if (!(wordStem.equalsIgnoreCase("dan") || wordStem.equalsIgnoreCase("atau"))) {
                    pattern.add("value");
                    sentence.add(_word);
                    sword.add(_word);
                    meaning.add(_word);

                }

            }
        }

        qexec.close();
    }

    public static String isValidSentence() {
        ArrayList<String> validPattern = new ArrayList<String>();
        ArrayList<String> validSentence = new ArrayList<String>();
        ArrayList<String> validMeaning = new ArrayList<String>();
        boolean isValid = true;
        String errMsg = "OK";
        //validPattern.clear();
        //validSentence.clear();
        int n = pattern.size();

        if (n < 3) {
            System.out.println("ini ke 1 ");
            isValid = false;
            errMsg = "Terdapat Kesalahan -<b> Kalimat Pencarian Tidak Lengkap </b>  ";
        }
        // first pattern must be "class"
        if ((isValid) && (pattern.get(0).compareToIgnoreCase("class") != 0)) {
            isValid = false;
            System.out.println("ini ke 2 ");
            //    errMsg=pattern.get(0);
            errMsg = "Terdapat Kesalahan -<b> " + pattern.get(0).toString() + "</b> - Kata pertama seharusnya berupa <b>manfaat,tanaman,etnis,klasifikasi,habitat</b> ";
        }
        if ((isValid) && (pattern.get(1).compareToIgnoreCase("properti") != 0)) {
            isValid = false;
            errMsg = "Terdapat Kesalahan -<b> " + pattern.get(2).toString() + "</b> - kata kedua seharusnya berupa properti</b> ";
        }

        // last pattern must be "value"
        if (isValid) {
            System.out.println("ini ke 3 ");
            if (pattern.get(n - 1).compareToIgnoreCase("value") != 0) {
                //dijadikan value saja
                pattern.set(n - 1, "value");
                sentence.set(n - 1, sword.get(n - 1).toString());
                meaning.set(n - 1, sword.get(n - 1).toString());
                //isValid = false;
                errMsg = "Pertanyaan harus diakhiri dengan suatu 'Value', seperti nama tanaman, nama penyakit, dll";
            }
        }

        if (isValid) {
            System.out.println("ini ke 4 ");
            String oldPattern = "";
            //theClass = sentence.get(0);

            for (int i = n - 1; i > 0; i--) {
                if (pattern.get(i).trim().length() == 0) {
                    continue;
                }
                // no pattern "class" other than first pattern
                if (pattern.get(i).compareToIgnoreCase("class") == 0) {
                    isValid = false;
                    break;
                }

                if ((oldPattern.equalsIgnoreCase("properti")) && (pattern.get(i).equalsIgnoreCase(oldPattern))) {
                    //--------
                } else {
                    oldPattern = pattern.get(i);
                    validPattern.add(pattern.get(i));
                    validSentence.add(sentence.get(i));
                    validMeaning.add(meaning.get(i));
                }
            }

            if (isValid) {
                System.out.println("ini ke 5 ");
                validPattern.add("class");
                validSentence.add(theClass);
                validMeaning.add(theClassMeaning);

                pattern.clear();
                sentence.clear();
                meaning.clear();
                //-- put the valid sentence into sentence, using ascending order
                for (int i = validPattern.size() - 1; i >= 0; i--) {
                    pattern.add(validPattern.get(i));
                    sentence.add(validSentence.get(i));
                    meaning.add(validMeaning.get(i));
                }

            }
        }

        if (errMsg.equalsIgnoreCase("OK")) {
            System.out.println("ini ke 6 ");
            createArrayFromPattern();
        }

        return errMsg;
    }

    public static String createQuery() {
        String str = "";
        String theValue = "";
        String filterClause = "";
        String queryPlus = "";
        System.out.println("dsa" + sentence.get(1));
        int n = pattern.size();
        if (sentence.get(0).equalsIgnoreCase("tanaman")) {
            countQuery = "SELECT DISTINCT ?s ";
            if (sentence.get(1).equalsIgnoreCase("namaManfaat")) {
                str = "SELECT DISTINCT "
                        + "?namaTanaman "
                        + " (SAMPLE(?namaLatin) AS ?latin)"
                        + " (group_concat(distinct ?namaHabitat;separator=\" | \") as ?habitat)"
                        + " (SAMPLE(?namaHabitus) AS ?habitus) "
                        + " (SAMPLE(?pHRange) AS ?RangepH) "
                        + "(SAMPLE(?tanamanDaerah) AS ?daerahTanaman) "
                        + "(SAMPLE(?namaEtnis) AS ?etnis) "
                        + "(SAMPLE(?namaBagianTanaman) AS ?bagianTanaman) "
                        + "(SAMPLE(?jenisManfaat) AS ?jnsManfaat)  "
                        + "(SAMPLE(?namaManfaat) AS ?manfaat)  "
                        + "(SAMPLE(?namaCaraPenggunaan) AS ?penggunaan) ";

                queryPlus = ""
                        + " WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                        + "?s <http://example.org/ethnomed#manfaatTanaman> ?manfaatTanaman ."
                        + "?s <http://example.org/ethnomed#pHRange> ?pHRange ."
                        + "?s <http://example.org/ethnomed#kategoripH> ?kategoripH ."
                        + "?s <http://example.org/ethnomed#tanamanDaerah> ?tanamanDaerah ."
                        + "?s <http://example.org/ethnomed#hasHabitat> ?ha ."
                        + "?ha <http://example.org/ethnomed#namaHabitat> ?namaHabitat ."
                        + "?s <http://example.org/ethnomed#hasHabitus> ?hu ."
                        + "?hu <http://example.org/ethnomed#namaHabitus> ?namaHabitus ."
                        + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                        + "?e <http://example.org/ethnomed#namaEtnis> ?namaEtnis ."
                        + "?s <http://example.org/ethnomed#hasBagianTanaman> ?bt ."
                        + "?bt <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?bt <http://example.org/ethnomed#hasManfaat> ?m ."
                        + "?m <http://example.org/ethnomed#jenisManfaat> ?jenisManfaat ."
                        + "?m <http://example.org/ethnomed#namaManfaat> ?namaManfaat ."
                        + "?m <http://example.org/ethnomed#hasCaraPenggunaan> ?p ."
                        + "?p <http://example.org/ethnomed#namaCaraPenggunaan> ?namaCaraPenggunaan ."
                        + "FILTER ((?namaTanaman != '')) ";

            } else if (sentence.get(1).equalsIgnoreCase("namaKlasifikasi")) {
                str = "SELECT DISTINCT "
                        + "?namaTanaman "
                        + " (SAMPLE(?namaKlasifikasi) AS ?klasifikasi)"
                        + " (SAMPLE(?namaLatin) AS ?latin)"
                        + " (group_concat(distinct ?namaHabitat;separator=\" | \") as ?habitat)"
                        + " (SAMPLE(?namaHabitus) AS ?habitus) "
                        + " (SAMPLE(?pHRange) AS ?RangepH) "
                        + "(SAMPLE(?tanamanDaerah) AS ?daerahTanaman) "
                        + "(SAMPLE(?namaEtnis) AS ?etnis) "
                        + "(SAMPLE(?namaBagianTanaman) AS ?bagianTanaman) "
                        + "(SAMPLE(?jenisManfaat) AS ?jnsManfaat)  "
                        + "(SAMPLE(?namaManfaat) AS ?manfaat)  "
                        + "(SAMPLE(?namaCaraPenggunaan) AS ?penggunaan) ";

                queryPlus = ""
                        + " WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                        + "?s <http://example.org/ethnomed#manfaatTanaman> ?manfaatTanaman ."
                        + "?s <http://example.org/ethnomed#pHRange> ?pHRange ."
                        + "?s <http://example.org/ethnomed#kategoripH> ?kategoripH ."
                        + "?s <http://example.org/ethnomed#tanamanDaerah> ?tanamanDaerah ."
                        + "?s <http://example.org/ethnomed#hasKlasifikasi> ?k ."
                        + "?k <http://example.org/ethnomed#namaKlasifikasi> ?namaKlasifikasi ."
                        + "?s <http://example.org/ethnomed#hasHabitat> ?ha ."
                        + "?ha <http://example.org/ethnomed#namaHabitat> ?namaHabitat ."
                        + "?s <http://example.org/ethnomed#hasHabitus> ?hu ."
                        + "?hu <http://example.org/ethnomed#namaHabitus> ?namaHabitus ."
                        + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                        + "?e <http://example.org/ethnomed#namaEtnis> ?namaEtnis ."
                        //                    + "?e <http://example.org/ethnomed#bagianTanamanYgDigunakanEtnis> ?bagianTanamanYgDigunakanEtnis ."
                        //                    + "?e <http://example.org/ethnomed#manfaatOlehEtnis> ?manfaatOlehEtnis ."
                        //                    + "?e <http://example.org/ethnomed#caraPenggunaanOlehEtnis> ?caraPenggunaanOlehEtnis ."
                        + "?s <http://example.org/ethnomed#hasBagianTanaman> ?bt ."
                        + "?bt <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?bt <http://example.org/ethnomed#hasManfaat> ?m ."
                        + "?m <http://example.org/ethnomed#jenisManfaat> ?jenisManfaat ."
                        + "?m <http://example.org/ethnomed#namaManfaat> ?namaManfaat ."
                        + "?m <http://example.org/ethnomed#hasCaraPenggunaan> ?p ."
                        + "?p <http://example.org/ethnomed#namaCaraPenggunaan> ?namaCaraPenggunaan ."
                        + "FILTER ((?namaTanaman != '')) ";

            } else {
                str = "SELECT DISTINCT "
                        + "?namaTanaman "
                        + " (SAMPLE(?namaLatin) AS ?latin)"
                        + " (group_concat(distinct ?namaHabitat;separator=\" | \") as ?habitat)"
                        + " (SAMPLE(?namaHabitus) AS ?habitus) "
                        + " (SAMPLE(?pHRange) AS ?RangepH) "
                        + "(SAMPLE(?tanamanDaerah) AS ?daerahTanaman) "
                        + " (SAMPLE(?namaKlasifikasi) AS ?klasifikasi)"
                        + "(SAMPLE(?namaEtnis) AS ?etnis) "
                        + "(SAMPLE(?namaBagianTanaman) AS ?bagianTanaman) "
                        + "(SAMPLE(?jenisManfaat) AS ?jnsManfaat)  "
                        + "(SAMPLE(?namaManfaat) AS ?manfaat)  "
                        + "(SAMPLE(?namaCaraPenggunaan) AS ?penggunaan) ";
                queryPlus = ""
                        + " WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                        + "?s <http://example.org/ethnomed#manfaatTanaman> ?manfaatTanaman ."
                        + "?s <http://example.org/ethnomed#pHRange> ?pHRange ."
                        + "?s <http://example.org/ethnomed#kategoripH> ?kategoripH ."
                        + "?s <http://example.org/ethnomed#tanamanDaerah> ?tanamanDaerah ."
                        + "?s <http://example.org/ethnomed#hasKlasifikasi> ?k ."
                        + "?k <http://example.org/ethnomed#namaKlasifikasi> ?namaKlasifikasi ."
                        + "?s <http://example.org/ethnomed#hasHabitat> ?ha ."
                        + "?ha <http://example.org/ethnomed#namaHabitat> ?namaHabitat ."
                        + "?s <http://example.org/ethnomed#hasHabitus> ?hu ."
                        + "?hu <http://example.org/ethnomed#namaHabitus> ?namaHabitus ."
                        + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                        + "?e <http://example.org/ethnomed#namaEtnis> ?namaEtnis ."
                        //                    + "?e <http://example.org/ethnomed#bagianTanamanYgDigunakanEtnis> ?bagianTanamanYgDigunakanEtnis ."
                        //                    + "?e <http://example.org/ethnomed#manfaatOlehEtnis> ?manfaatOlehEtnis ."
                        //                    + "?e <http://example.org/ethnomed#caraPenggunaanOlehEtnis> ?caraPenggunaanOlehEtnis ."
                        + "?s <http://example.org/ethnomed#hasBagianTanaman> ?bt ."
                        + "?bt <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?bt <http://example.org/ethnomed#hasManfaat> ?m ."
                        + "?m <http://example.org/ethnomed#jenisManfaat> ?jenisManfaat ."
                        + "?m <http://example.org/ethnomed#namaManfaat> ?namaManfaat ."
                        + "?m <http://example.org/ethnomed#hasCaraPenggunaan> ?p ."
                        + "?p <http://example.org/ethnomed#namaCaraPenggunaan> ?namaCaraPenggunaan ."
                        + "FILTER ((?namaTanaman != '')) ";
            }

        } else if (sentence.get(0).equalsIgnoreCase("Manfaat") || sentence.get(0).equalsIgnoreCase("obat")) {

            countQuery = "SELECT DISTINCT ?s ";
            if (sword.get(1).equalsIgnoreCase("obat") && sentence.get(1).equalsIgnoreCase("namaTanaman")) {
                str = "SELECT DISTINCT ?namaTanaman (?manfaatTanamanObat AS ?namaManfaat) ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#manfaatTanamanObat> ?manfaatTanamanObat ."
                        + "FILTER ((?manfaatTanamanObat != '')) ";
            } else if (sword.get(1).equalsIgnoreCase("non_obat") && sentence.get(1).equalsIgnoreCase("namaTanaman")) {
                str = "SELECT DISTINCT ?namaTanaman (?manfaatTanamanNonObat AS ?namaManfaat) ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#manfaatTanamanNonObat> ?manfaatTanamanNonObat ."
                        + "FILTER ((?manfaatTanamanNonObat != '')) ";
            }
            else if (sword.get(1).equalsIgnoreCase("obat") && sentence.get(1).equalsIgnoreCase("namaBagianTanaman")) {
                str = "SELECT DISTINCT  ?namaBagianTanaman (?BTObat AS ?namaManfaat) ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?s <http://example.org/ethnomed#BTObat> ?BTObat ."
                        + "FILTER ((?BTObat != '')) ";
            }
            else if (sword.get(1).equalsIgnoreCase("obatt") && sentence.get(1).equalsIgnoreCase("namaBagianTanaman")) {
                str = "SELECT DISTINCT  ?namaBagianTanaman (?BTObat AS ?namaManfaat) ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?s <http://example.org/ethnomed#BTObat> ?BTObat ."
                        + "FILTER ((?BTObat != '')) ";
            } else if (sword.get(1).equalsIgnoreCase("non_obat") && sentence.get(1).equalsIgnoreCase("namaBagianTanaman")) {
                str = "SELECT DISTINCT  ?namaBagianTanaman (?BTNonObat AS ?namaManfaat) ";
                queryPlus = ""
                        + "WHERE { "
                             + "?s <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?s <http://example.org/ethnomed#BTNonObat> ?BTNonObat ."
                        + "FILTER ((?BTNonObat != '')) ";
            }
            else if (sword.get(1).equalsIgnoreCase("tanam")) {
                str = "SELECT DISTINCT ?namaTanaman (?manfaatTanaman AS ?namaManfaat) ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                         + "?s <http://example.org/ethnomed#manfaatTanaman> ?manfaatTanaman ."
                        + "FILTER ((?manfaatTanaman != '')) ";
            } else {
                    str = "SELECT DISTINCT ?namaTanaman ?namaLatin  ?jenisManfaat ?namaBagianTanaman ?namaManfaat ";
                queryPlus = ""
                        + "WHERE { ?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                        + "?s <http://example.org/ethnomed#hasBagianTanaman> ?bt ."
                        + "?bt <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "?bt <http://example.org/ethnomed#hasManfaat> ?m ."
                        + "?m <http://example.org/ethnomed#namaManfaat> ?namaManfaat ."
                        + "?m <http://example.org/ethnomed#jenisManfaat> ?jenisManfaat ."
                        + "FILTER ((?namaManfaat != '')) ";
            }

        } else if (sentence.get(0).equalsIgnoreCase("Klasifikasi")) {
            countQuery = "SELECT DISTINCT ?s ";
            str = "SELECT DISTINCT "
                    + "?namaTanaman ?namaLatin ?tingkatKlasifikasi ?namaKlasifikasi ";
            queryPlus = ""
                    + "WHERE { "
                    + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                    + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                    + "?s <http://example.org/ethnomed#hasKlasifikasi> ?k ."
                    + "?k <http://example.org/ethnomed#namaKlasifikasi> ?namaKlasifikasi ."
                    + "?k <http://example.org/ethnomed#tingkatKlasifikasi> ?tingkatKlasifikasi ."
                    + "FILTER ((?namaKlasifikasi != '')) ";

        } else if (sentence.get(0).equalsIgnoreCase("habitat")) {
            countQuery = "SELECT DISTINCT ?s ";
            str = "SELECT DISTINCT "
                    + "?namaTanaman ?namaLatin ?namaHabitat ?namaHabitus ?namaDaerah ";
            queryPlus = ""
                    + "WHERE { "
                    + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                    + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                    + "?s <http://example.org/ethnomed#hasHabitat> ?ha ."
                    + "?ha <http://example.org/ethnomed#namaHabitat> ?namaHabitat ."
                    + "?s <http://example.org/ethnomed#hasHabitus> ?hu ."
                    + "?hu <http://example.org/ethnomed#namaHabitus> ?namaHabitus ."
                    + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                    + "?e <http://example.org/ethnomed#hasDaerah> ?d ."
                    + "?d <http://example.org/ethnomed#namaDaerah> ?namaDaerah ."
                    + "FILTER ((?namaHabitat != '')) ";

        } else if (sentence.get(0).equalsIgnoreCase("Penggunaan")) {
            countQuery = "SELECT DISTINCT ?s ";
            str = "SELECT DISTINCT "
                    + "?namaTanaman ?namaLatin ?namaBagianTanaman ?namaManfaat ?namaCaraPenggunaan ";
            queryPlus = ""
                    + "WHERE { "
                    + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                    + "?s <http://example.org/ethnomed#namaLatin> ?namaLatin ."
                    + "?s <http://example.org/ethnomed#hasBagianTanaman> ?bt ."
                    + "?bt <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                    + "?bt <http://example.org/ethnomed#hasManfaat> ?m ."
                    + "?m <http://example.org/ethnomed#namaManfaat> ?namaManfaat ."
//                    + "?m <http://example.org/ethnomed#jenisManfaat> ?jenisManfaat ."
                    + "?m <http://example.org/ethnomed#hasCaraPenggunaan> ?p ."
                    + "?p <http://example.org/ethnomed#namaCaraPenggunaan> ?namaCaraPenggunaan ."
                    + "FILTER ((?namaCaraPenggunaan != '')) ";
        } else if (sentence.get(0).equalsIgnoreCase("Etnis")) {

            if (sentence.get(1).equalsIgnoreCase("namaTanaman")) {
                countQuery = "SELECT DISTINCT ?s ";
                str = "SELECT DISTINCT "
                        + "?namaTanaman  ?namaEtnis ?namaDaerah ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                        + "?e <http://example.org/ethnomed#namaEtnis> ?namaEtnis ."
//                        + "?s <http://example.org/ethnomed#tanamanDaerah> ?tanamanDaerah ."
                        + "?e <http://example.org/ethnomed#hasDaerah> ?d ."
                        + "?d <http://example.org/ethnomed#namaDaerah> ?namaDaerah ."
                        + "";
            } else {
                countQuery = "SELECT DISTINCT ?s ";
                str = "SELECT DISTINCT "
                        + "?namaTanaman  ?namaEtnis ?namaDaerah ?namaBagianTanaman ";
                queryPlus = ""
                        + "WHERE { "
                        + "?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman ."
                        + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                        + "?e <http://example.org/ethnomed#namaEtnis> ?namaEtnis ."
                        + "?e <http://example.org/ethnomed#hasDaerah> ?d ."
                        + "?d <http://example.org/ethnomed#namaDaerah> ?namaDaerah ."
                        + "?s <http://example.org/ethnomed#hasBagianTanaman> ?bt ."
                        + "?bt <http://example.org/ethnomed#namaBagianTanaman> ?namaBagianTanaman ."
                        + "";
            }

        }

        str += queryPlus;
        countQuery += queryPlus;

        //-------------
        String filter = createWhereQuery();
        str += filter;
        countQuery += filter;
        //-------------
        String strEnd = endQuery();
        str += strEnd;
        countQuery += strEnd;
        return str;
    }

    // all ordered by tanaman
    static String endQuery() {
        String endString = "}";
        if (sentence.get(0).equalsIgnoreCase("tanaman")) {
            endString += "GROUP BY ?namaTanaman  ";
        }
       else if (sword.get(1).equalsIgnoreCase("obat") && sentence.get(1).equalsIgnoreCase("namaBagianTanaman")){
        endString += " ORDER BY ?namaBagianTanaman ";
        }
      else  if (sword.get(1).equalsIgnoreCase("non_obat") && sentence.get(1).equalsIgnoreCase("namaBagianTanaman")){
        endString += " ORDER BY ?namaBagianTanaman ";
        }
      else {
                endString += " ORDER BY ?namaTanaman ";
      }
  
        return endString;
    }

    public static String createWhereQuery() {
        // Create FILTER query from array aPattern
        //String cFilter = "FILTER ((?restaurant_name != '') ";
        String cFilter = " FILTER (regex(?" + aPattern[0][0] + ",'" + aPattern[0][1] + "','i')";
        for (int i = 1; i < aPattern.length; i++) {
            if (aPattern[i][3].trim().equalsIgnoreCase("atau")) {
                cFilter += " || ";
            } else {
                cFilter += ") FILTER (";
            }

            cFilter += "regex(?" + aPattern[i][0] + ",'" + aPattern[i][1] + "','i')";
        }
        cFilter += ")";
        return cFilter;
    }

    public static void createArrayFromPattern() {
        String theValue = "";
        String prop = "";
        String theMeaning = "";
        int n = pattern.size();
        int posArray = 0;
        int i = 1;
        // test = "";
        aPattern = new String[n][4];
        // [0] -> predicate/prop [1]->value [2]->meaning [3] -> dan / atau

        while (i < n) {
            if (i < n && pattern.get(i).trim().length() > 0) {
                // jika bukan value
                // test += " | PATTERN : "+pattern.get(i).toString()+" | ";
                if (i < n && pattern.get(i).equalsIgnoreCase("properti")) {
                    prop = sentence.get(i);
                    theMeaning = meaning.get(i);
                    //  test+= theMeaning+" > ";
                    i++;
                } else {
                    while (i < n && pattern.get(i).equalsIgnoreCase("value")) {
                        theValue += " " + sentence.get(i).trim();
                        i++;
                        test += " -> VAL : " + theValue + " | ";
                    }
                }
                if ((prop.trim().length() > 0) && theValue.trim().length() > 0) {
                    //Add to The Array
                    aPattern[posArray][0] = prop.trim();
                    aPattern[posArray][1] = theValue.trim();
                    aPattern[posArray][2] = theMeaning.trim();
                    //    test += " MASUK : "+aPattern[posArray][2].toString()+" # "+aPattern[posArray][1]+" $ ";
                    posArray++;
                }
                theValue = "";
            }
        }
        sortArrayPattern();

    }

    static void sortArrayPattern() {
        //resize array depend from not null value
        int a = 0;
        for (a = 0; a < aPattern.length; a++) {
            if (aPattern[a][0] == null) {
                break;
            }
        }
        aPattern = java.util.Arrays.copyOf(aPattern, a);

        System.out.println(aPattern);
        // Sort the Array aPattern
        String[][] aTemp = new String[1][3];
        for (int i = 1; i < aPattern.length; i++) {
            if (aPattern[i][0].compareTo(aPattern[i - 1][0]) < 0) {
                aTemp[0][0] = aPattern[i - 1][0];
                aTemp[0][1] = aPattern[i - 1][1];
                aTemp[0][2] = aPattern[i - 1][2];
                aPattern[i - 1][0] = aPattern[i][0];
                aPattern[i - 1][1] = aPattern[i][1];
                aPattern[i - 1][2] = aPattern[i][2];
                aPattern[i][0] = aTemp[0][0];
                aPattern[i][1] = aTemp[0][1];
                aPattern[i][2] = aTemp[0][2];
            }
        }
        // tentukan pake and atau or
        aPattern[0][3] = " ";
        for (int i = 1; i < aPattern.length; i++) {
            if (aPattern[i][0].equals(aPattern[i - 1][0])) {
                aPattern[i][3] = "atau";
            } else {
                aPattern[i][3] = "dan";
            }
        }

    }

    public static String showArrayPattern() {
        String res = "ARRAY : ";
        for (int i = 0; i < aPattern.length; i++) {
            res += aPattern[i][0] + " -> " + aPattern[i][1] + " -> " + aPattern[i][2] + "<br/>";
        }
        return res;
    }
}
