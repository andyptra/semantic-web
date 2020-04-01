<%--
    Document   : search1
    Created on : Aug 22, 2019, 1:35:14 PM
    Author     : asus
--%>
<%@page import="com.hp.hpl.jena.util.FileUtils"%>
<%@page import="org.topbraid.spin.model.Function"%>
<%@page import= " org.topbraid.spin.arq.SPINARQFunction" %>
<%@page import= " org.topbraid.spin.model.SPINFactory" %>
<%@page import= " com.hp.hpl.jena.rdf.model.Resource" %>
<%@page import= " myfunction.Stemmer"%>
<%@page import= "java.io.PrintWriter" %>
<%@page import= "com.hp.hpl.jena.ontology.OntModel" %>
<%@page import= "java.io.InputStream" %>
<%@page import= "org.topbraid.spin.arq.ARQ2SPIN" %>
<%@page import= "org.topbraid.spin.arq.ARQFactory" %>
<%@page import= "org.topbraid.spin.model.Select" %>
<%@page import= "org.topbraid.spin.system.SPINModuleRegistry" %>

<%@page import= "org.topbraid.spin.constraints.ConstraintViolation"%>
<%@page import= "org.topbraid.spin.constraints.SPINConstraints"%>
<%@page import= "org.topbraid.spin.inference.SPINInferences"%>
<%@page import= "org.topbraid.spin.system.SPINLabels"%>
<%@page import= "org.topbraid.spin.system.SPINModuleRegistry"%>


<%@page import= "com.hp.hpl.jena.query.*" %>
<%@page import= "com.hp.hpl.jena.ontology.OntModelSpec" %>
<%@page import= "com.hp.hpl.jena.util.FileManager"%>
<%@page import= "com.hp.hpl.jena.rdf.model.Model"%>
<%@page import= "com.hp.hpl.jena.rdf.model.ModelFactory"%>
<%@page import= "com.hp.hpl.jena.shared.ReificationStyle" %>
<%@page import= "java.io.InputStream" %>
<%@page import= "java.util.ArrayList" %>


<% //@page import= "myfunction.stemmer" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%!    private String keyword = null;
    private String cariAsli = null;
    public OntModel ontModel = null;
    public QueryExecution qexec = null;
    public Model wordModel = null;
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

%>

<%
            SPINModuleRegistry.get().init();
            // ambil data dari form pertanyaan di index.jsp
            keyword = request.getParameter("keyword");
            // ini variabel bantu untuk meyimpan isi pertanyaan
            // karena isi pertanyaan (cari) akan di rubah di bagian bawah
            cariAsli = keyword;
            //PrintWriter out = new PrintWriter(System.out);

%>

<%!    public Model getOntology(String ontFile) {
        Model baseModel = ModelFactory.createDefaultModel(ReificationStyle.Minimal);
        baseModel.read(ontFile);
        //baseModel.read("http://localhost/resto.owl");
        return baseModel;
    }

    public void getWordModel() {
        wordModel = ModelFactory.createDefaultModel(ReificationStyle.Minimal);
//        wordModel.read("http://localhost/d/words.owl");
         wordModel.read("http://localhost/words.owl");
    }

    //  public  getInferenceOntology() {
    public void getInferenceOntology() {
        Model baseModel;
        baseModel = getOntology("http://localhost/ethnomed.owl");
        //        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, baseModel);
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
        System.out.println("Inferred triples: " + newTriples.size());
        
//        //magic property example
//        Resource rsrc = ontModel.getResource("http://example.org/ethnomed#pHRange");
//  Function query = SPINFactory.asFunction(rsrc);
//  SPINARQFunction function = new SPINARQFunction(query);
//  Query select = function.getBodyQuery();
//  System.out.println(function);
        
        String query =
			"SELECT ?phrange" +
			"WHERE {" +
			" ?s <http://example.org/ethnomed#pHRange> ?pHRange ." +
			"}";
		
		Query arqQuery = ARQFactory.get().createQuery(ontModel, query);
		ARQ2SPIN arq2SPIN = new ARQ2SPIN(ontModel);
		Select spinQuery = (Select) arq2SPIN.createQuery(arqQuery, null);
		
		System.out.println("SPIN query in Turtle:");
		ontModel.write(System.out, FileUtils.langTurtle);
		
		System.out.println("-----");
		String str = spinQuery.toString();
		System.out.println("SPIN query:\n" + str);
		
		// Now turn it back into a Jena Query
		Query parsedBack = ARQFactory.get().createQuery(spinQuery);
		System.out.println("Jena query:\n" + parsedBack);
        
    }
  public String start() {
        getWordModel();
        getInferenceOntology();
        return "";
    }
    public ResultSet runQuery(String query) {
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

    public ResultSet runQueryWord(String query) {
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
    
 public  String tes_showResult() {
        String vResult = "Ini TES<br/><table border='1'>";
        String query = "";
        query = ""
//                 + "SELECT DISTINCT"
//                            + "?restaurantName ?menuName ?menuType ?categoryName ?restaurantAddress ?telephoneNumber "
//                            + "WHERE {"
//                            + "<http://example.org/restaurants#hasMenu> <http://example.org/restaurants#queryMenu> "
//                            + "(?s ?restaurantName ?restaurantAddress ?telephoneNumber ?menuName ?menuType ?menuMisc ?categoryName ?nearBy ?priceCategory ?nearBy) ."
//                            + "}";
                            

                            +"SELECT DISTINCT"
                                        + "?namaTanaman ?namaLatin ?namaKlasifikasi "
                                        + "WHERE {"
                                        + "<http://example.org/ethnomed#Genus> <http://example.org/ethnomed#queryKlasifikasi> "
                                        + "(?s ?namaTanaman ?namaLatin ?namaKlasifikasi ) ."
                                        + "}";

//                         + "SELECT DISTINCT"
//                            + "?x "
//                            + "WHERE {"
//                            + "<http://example.org/ethnomed#Obat> <http://example.org/restaurants#listOfManfaat> ?x ."
//                            + "}";

                               

          

        ResultSet rs = runQuery(query);
        if (rs == null) {
        } else {
//            for (; rs.hasNext();) {
            while (rs.hasNext()) {
                QuerySolution qs = rs.nextSolution();

                vResult += "<tr>"
                        +"<td> "
                        + qs.getLiteral("x").getValue().toString() + "</td><td> ";
//                        + qs.getLiteral("namaKlasifikasi").getValue().toString() + "</td><td> "
//                        + qs.getLiteral("tingkatKlasifikasi").getValue().toString() + "</td><td> ";
//                        + qs.getLiteral("menuType").getValue().toString() + "</td><td> "
//                        + qs.getLiteral("categoryName").getValue().toString() + "</td><td> "
//                        + qs.getLiteral("restaurantAddress").getValue().toString() + "</td><td> "
//                        + qs.getLiteral("telephoneNumber").getValue().toString() + "</td>";
                      
                vResult += "</tr>";
            }
        }
      qexec.close();
        vResult += "</table>";

        return vResult;
    }
    
    
    public String getData() {
          Stemmer strstem = new Stemmer();
          String _word = "tanaman";
        String wordStem = strstem.stem(_word).trim();

        System.out.println(wordStem);
       String _sentence = "";
        String _meaning = "";
        String res = "";
        String vResult = "Ini Data<br/><table border='1'>";
        String query =
                 "PREFIX words: <http://example.org/words#>"
         + "SELECT DISTINCT ?c ?fc ?w ?m ?sy  "
                + "WHERE {"
                   + "?k words:category ?c ."
                   + "?k <http://example.org/words#forclass> ?fc ."
                   + "?k <http://example.org/words#word> ?w ."
                   + "?k <http://example.org/words#meaning> ?m ."
                   + "?k <http://example.org/words#synonim> ?sy ."
        + "FILTER(?sy ='non_obat' && ?fc='all')"
//                   + "FILTER(?sy ='etnis')"
                //  +"FIlTER(?sy='batuk'  && ?c!='class' && (?fc = 'obat' || ?fc = 'all' ))"
                 + " } ";


        ResultSet rs = runQueryWord (query);
        if (rs == null) {
  vResult = "gada";
        } else {

            for (; rs.hasNext();) {
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

                if (qs.getLiteral("c").getValue().toString().trim().equalsIgnoreCase("prop")) {
                    lastProperty = _sentence;
                }
                    theClass = _sentence;
                    theClassMeaning = _meaning;


                vResult += "<tr><td> "
                        + qs.getLiteral("c").getValue().toString() + "</td>";

                  vResult += "<td> "
                        + qs.getLiteral("fc").getValue().toString() + "</td>";

                vResult += "<td> "
                        + qs.getLiteral("w").getValue().toString() + "</td>";

                vResult += "<td> "
                        + qs.getLiteral("m").getValue().toString() + "</td>";

                vResult += "<td> "
                        + qs.getLiteral("sy").getValue().toString() + "</td>";
//                vResult += "<td> "
//                        + wordStem + "</td>";
//                   vResult += "<td> "
//                        + pattern + "</td>";
//                      vResult += "<td> "
//                        + sentence + "</td>";
                vResult += "</tr>";

            }




            }
        qexec.close();
        vResult += "</table>";


        return vResult;

    }

       public String getData2() {
        String query = "";
        String stopWord = "";
        String phrase = "";
        query = ""
                + "select ?a "
                + " where { "
                + " ?s <http://example.org/words#stopword> ?a . "
                + " } ";

        // Filtering
        ResultSet rs = runQueryWord(query);
        if (rs == null) {
        } else {
            for (; rs.hasNext();) {
                QuerySolution qs = rs.nextSolution();
                stopWord = qs.getLiteral("a").getValue().toString();
                keyword = keyword.replaceAll(stopWord, " ");
            }
        }
        qexec.close();

        // tokenization
        String token[];
        String SearchKey ="";
        token = keyword.split(" ");
        //for (int i=0; i < token.length ; i++){
        for (String word : token){
            if (word.isEmpty()) continue;
            System.out.println(word);

        }
        return keyword;
    }


%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
         <%=start()%>
        <%=getData()%>
          <%=tes_showResult()%>
        <%--<%=getData2()%>--%>
    </body>
</html>
