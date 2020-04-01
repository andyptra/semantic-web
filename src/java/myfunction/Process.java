/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfunction;

//import myfunction.Library;
//import myfunction.Stemmer;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.text.NumberFormat;
//import java.io.PrintWriter ;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import org.json.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.net.ssl.HttpsURLConnection;
import static myfunction.Library.sentence;

/**
 *
 * @author asus
 */
public class Process {

    public static String normalis(String _key) throws Exception {
        System.setProperty("javax.net.debug", "all");
        String url = "https://api.prosa.ai/v1/normals";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("x-api-key", "0h5l9smIzzy9kTtkojzihKreS93g7sFwqaNaObAb");
        String input = "{\"text\":\"" + _key + "\"}";
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(input);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JsonReader reader = Json.createReader(in);
        JsonObject object = reader.readObject();
        String s = object.getString("text");
        return s;

    }

    public static String getData(String _keyword) throws Exception {

        String str = _keyword.toLowerCase();
        String word, wordStem, wordCek, result = "#N/A";
        String stop = Library.stopWord;
        String question = Library.questionWord;
        Library.pattern.clear();
        Library.sentence.clear();
        Library.sword.clear();
        Library.meaning.clear();
//        str = str.replaceAll("bagian tanaman", "bagiantanaman");
        String getFirstStr = _keyword.toLowerCase();
        String dtFirst[];
        dtFirst = getFirstStr.split(" ");
        String valFirst = dtFirst[0].trim();
        
        if(valFirst.equalsIgnoreCase("bagian")){
            valFirst = "bagian_tanaman";
        }
        
        Library.parsing(valFirst, 1);
//        str = str.replaceAll("5-6", "56");
//        str = str.replaceAll("4-5", "45");
//        str = str.replaceAll("6-7", "67");
//        str = str.replaceAll("5-7", "57");
//        str = str.replaceAll("5-8", "58");
        str = str.replaceAll("termasuk divisi", "termasuk");
           str = str.replaceAll("bagian tanaman", "bagian_tanaman");  
        str = str.replaceAll("cara penggunaan", "penggunaan");
        str = str.replaceAll("cara pengolahan", "penggunaan");
        str = str.replaceAll("cara pemakaian", "penggunaan");
        str = str.replaceAll("tembelekkan", "tembelekan");
        str = str.replaceAll("non obat", "non_obat");
        str = str.replaceAll("penyakit yang dapat", "obat");
        str = str.replaceAll("ph range", "range");
        str = str.replaceAll("rentang ph", "range");

        if (Library.sword.get(0).equals("etnis") || Library.sword.get(0).equals("suku") || Library.sword.get(0).equals("ethnis")) {
            str = str.replaceAll("pengguna akar", "guna_akar akar");
            str = str.replaceAll("pengguna minyak", "guna_minyak minyak");
            str = str.replaceAll("pengguna batang", "guna_batang batang");
            str = str.replaceAll("pengguna biji", "guna_biji biji");
            str = str.replaceAll("pengguna buah", "guna_buah buah");
            str = str.replaceAll("pengguna bunga", "guna_bunga bunga");
            str = str.replaceAll("pengguna daun", "guna_daun daun");
            str = str.replaceAll("pengguna getah", "guna_getah getah");
            str = str.replaceAll("pengguna kulit", "guna_kulit kulit");
            str = str.replaceAll("pengguna kulit ", "guna_kulit_batang kulit batang");
            str = str.replaceAll("pengguna rimpang", "guna_rimpang rimpang");
            str = str.replaceAll("pengguna kayu", "guna_kayu kayu");
        }
        if (Library.sword.get(0).equals("manfaat") || Library.sword.get(0).equals("guna") || Library.sword.get(0).equals("cara")) {
            str = str.replaceAll("minyak", "bagian minyak");
            str = str.replaceAll("akar", "bagian akar");
            str = str.replaceAll("batang", "bagian batang");
            str = str.replaceAll("biji", "bagian biji");
            str = str.replaceAll("buah", "bagian buah");
            str = str.replaceAll("bunga", "bagian bunga");
            str = str.replaceAll("daun", "bagian daun");
            str = str.replaceAll("getah", "bagian getah");
            str = str.replaceAll("kulit", "bagian kulit");
            str = str.replaceAll("kulit batang", "bagian kulit batang");
            str = str.replaceAll("rimpang", "bagian rimpang");
        }
        if (Library.sword.get(0).equals("sakit")) {

            str = str.replaceAll("diobati dengan akar", "obatt dengan akar");
            str = str.replaceAll("diobati dengan batang", "obatt dengan batang");
            str = str.replaceAll("diobati dengan biji", "obatt dengan biji");
            str = str.replaceAll("diobati dengan buah", "obatt dengan buah");
            str = str.replaceAll("diobati dengan bunga", "obatt dengan bunga");
            str = str.replaceAll("diobati dengan daun", "obatt dengan daun");
            str = str.replaceAll("diobati dengan getah", "obatt dengan getah");
            str = str.replaceAll("diobati dengan kulit", "obatt dengan kulit");
            str = str.replaceAll("diobati dengan kulit batang", "obatt dengan kulit batang");
            str = str.replaceAll("diobati dengan rimpang", "obatt dengan rimpang");
            str = str.replaceAll("diobati dengan minyak", "obatt dengan minyak");
            
            str = str.replaceAll("disembukan dengan akar", "obatt dengan akar");
            str = str.replaceAll("disembukan dengan batang", "obatt dengan batang");
            str = str.replaceAll("disembukan dengan biji", "obatt dengan biji");
            str = str.replaceAll("disembukan dengan buah", "obatt dengan buah");
            str = str.replaceAll("disembukan dengan bunga", "obatt dengan bunga");
            str = str.replaceAll("disembukan dengan daun", "obatt dengan daun");
            str = str.replaceAll("disembukan dengan getah", "obatt dengan getah");
            str = str.replaceAll("disembukan dengan kulit", "obatt dengan kulit");
            str = str.replaceAll("disembukan dengan kulit batang", "obatt dengan kulit batang");
            str = str.replaceAll("disembukan dengan rimpang", "obatt dengan rimpang");
            str = str.replaceAll("disembukan dengan minyak", "obatt dengan minyak");
        }

        //- Tokenization
        String token[];
        token = str.split(" ");
        Stemmer strstem = new Stemmer();

        //- checking for questionword in first word
        wordStem = strstem.stem(token[0]).trim();
        word = ".+" + wordStem + ".+";
//        if (!question.matches(word)) {
//            return "Err : -<b> " + wordStem + " </b>- Kalimat Mestinya Harus diawali dengan kata perintah: -<b> " + question + " </b>-";
//        }

        //---
        int pos = 0;  // counter to give tag for first word
        for (int z = 0; z < token.length; z++) {
            // prepare word to match with stopword
            word = token[z].trim();
            if (word.isEmpty()) {
                continue; // kalau kosong lewati ke token berikutnya
            }
            wordCek = ".+" + strstem.stem(word) + ".+";

            if (stop.matches(wordCek)) {
                continue; // kalo ada stopword dilewati ke token berikutnya
            }
            pos++; // masuk ke prosedur representasi kalimat      
            Library.parsing(word, pos);

            System.out.println(word);
            System.out.println(pos);

//                result += word+" ";
        }
        String err = Library.isValidSentence();
        /*
         if (err.compareToIgnoreCase("OK") != 0) {
         result = err;
         } else {
         result = "OK";
         }
         */
        return err;
    }

    public static String showList() {
        String res = "<br/> SENTENCE : ";           //words
        for (String key : Library.sentence) {
            res += key + " - ";
        }
        res += "<br/> SWORD : ";                    //words
        for (String key : Library.sword) {
            res += key + " - ";
        }
        res += "<br/> PATTERN : ";                  //categori
        for (String key : Library.pattern) {
            res += key + " - ";
        }
        res += "<br/> Hasil Pencarian Anda : ";
        for (String key : Library.meaning) {
            res += key + " ";
        }

        return res;
    }

    static int countOfFoundedData() {
        int dataFound = 0;

        // cari dulu banyak datanya
        ResultSet rsCount = Library.runQuery(Library.countQuery);
        if (rsCount == null) {
            return 0;
        } else {
            while (rsCount.hasNext()) {
                QuerySolution qs = rsCount.nextSolution();
                dataFound++;
            }
        }
        return dataFound;
    }

    public static String showResult() throws JSONException {
        String vResult = "Tidak Menemukan Data";
        String query = Library.createQuery();
        int dataFound = countOfFoundedData();
        ResultSet rs = Library.runQuery(query);
        if ((rs != null) && (dataFound > 0)) {
            vResult = "hore Menemukan Data";
            if (Library.theClass.equalsIgnoreCase("tanaman")) {
                vResult = showTanaman(rs, dataFound);
            } else if (Library.theClass.equalsIgnoreCase("manfaat")) {
                vResult = showManfaat(rs, dataFound);
            } else if (Library.theClass.equalsIgnoreCase("obat")) {
                vResult = showObat(rs, dataFound);
            } else if (Library.theClass.equalsIgnoreCase("klasifikasi")) {
                vResult = showKlasifikasi(rs, dataFound);
            } else if (Library.theClass.equalsIgnoreCase("habitat")) {
                vResult = showHabitat(rs, dataFound);
            } else if (Library.theClass.equalsIgnoreCase("penggunaan")) {
                vResult = showPenggunaan(rs, dataFound);
            } else if (Library.theClass.equalsIgnoreCase("etnis")) {
                vResult = showEtnis(rs, dataFound);
            }
        } else {
            vResult = "Maaf tidak menemukan Data";
        }

        Library.qexec.close();

        return vResult;
    }

    static String showTime() {
        NumberFormat fmt = NumberFormat.getInstance();
        fmt.setMaximumFractionDigits(3); // 3 dp floating
        float time = (System.nanoTime() - Library.timeNeeded) / 1000000;
        return fmt.format(time / 1000);

    }

    static String showTanaman(ResultSet rs, int dataFound) throws JSONException {
        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        String TanamanName = "";
        String idTanaman = "";
        String JnsObat = "";
        String JnsObatz = "OBAT";
        String JnsNonObatz = "NON_OBAT";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String json = new String(outputStream.toByteArray());
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsonArray = jsnobject.getJSONObject("results");
        JSONArray bindings = jsonArray.getJSONArray("bindings");
        vResult += "<div class='row'>";
        vResult += " <div class='col-md-5'>";
        vResult += " <table class='table  table-borderless table-condensed table-hover'>";
        vResult += "     <thead>";
        vResult += "  <tr>";
        vResult += "    <th colspan='2' scope='col'>HASIL OUTPUT :</th>";
        vResult += "  </tr>";
        vResult += " </thead>";
        vResult += " <tbody>";
        vResult += " <tr>";
        vResult += "    <th scope='row'>Tanaman</th>";
        vResult += "    <td>:";
        String str1 = "s";
        for (int i = 0; i < bindings.length(); i++) {
            str1 = bindings.getJSONObject(i).getJSONObject("namaTanaman").getString("value") + ", ";
            vResult += str1;
        }
        vResult += "</td>";
        vResult += "  </tr>";
        vResult += "  <tr>";
        vResult += "       <th scope='row'>Nama Latin</th>";
        vResult += "        <td>:";
        for (int i = 0; i < bindings.length(); i++) {
            str1 = bindings.getJSONObject(i).getJSONObject("latin").getString("value") + ", ";
            vResult += str1;
        }
        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  </tbody>";
        vResult += "  </table>";
        vResult += " </div>";
        vResult += "</div>";

        vResult += " <div class='col-md-12'>";
        vResult += "   <button type='button' class='btn btn-primary' data-toggle='collapse' data-target='#demo'>SHOW MORE</button><br/><br/>";
        vResult += "  </div>";
        vResult += "</div>";
        vResult += "<div id='demo' class='collapse'>";
        if (bindings.length() > 1) {
            vResult += "    <div class='col-md-12'>";
            vResult += "      <table class='table table-bordered'>";
            vResult += "       <thead>";
            vResult += "         <tr>";
            vResult += "            <th scope='col'>Habitat & Habitus</th>";
            vResult += "            <th scope='col'>Bagian yang digunakan</th>";
            vResult += "            <th scope='col'>PH Tanaman</th>";
            vResult += "            <th scope='col'>Asal Daerah</th>";
            vResult += "            <th scope='col'>Etnis Pengguna</th>";
            vResult += "           <th scope='col'>Manfaat</th>";
            vResult += "           <th scope='col'>Cara Pengguanaan</th>";
            vResult += "       </tr>";
            vResult += "     </thead>";
            vResult += "     <tbody>";

            for (int i = 0; i < bindings.length(); i++) {
                vResult += "<tr>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("habitat").getString("value") + " & " + bindings.getJSONObject(i).getJSONObject("habitus").getString("value") + "</td>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("bagianTanaman").getString("value") + "</td>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("RangepH").getString("value") + "</td>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("daerahTanaman").getString("value") + "</td>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("etnis").getString("value") + "</td>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("manfaat").getString("value") + "</td>";
                vResult += " <td>" + bindings.getJSONObject(i).getJSONObject("penggunaan").getString("value") + "</td>";
                vResult += "</tr>";

            }
            vResult += " </tbody>";
            vResult += "  </table>";
            vResult += "  </div>";
            vResult += "</div>";
            vResult += "</ul></td></tr></table>";
            vResult += "  </div>";
            vResult += "</div>";
        } else {
            vResult += " <div class='row'>";
            vResult += " <div class='col-md-5'>";
            vResult += "    <table class='table  table-borderless table-condensed table-hover'>";
            vResult += "     <tbody>";
            vResult += "   <tr>";
            vResult += "       <th scope='row'>Habitat</th>";
            vResult += "        <td>:" + bindings.getJSONObject(0).getJSONObject("habitat").getString("value") + "</td>";
            vResult += "     </tr>";
            vResult += "     <tr>";
            vResult += "        <th scope='row'>Habitus</th>";
            vResult += "        <td>:" + bindings.getJSONObject(0).getJSONObject("habitus").getString("value") + "</td>";
            vResult += "     </tr>";
            vResult += "     <tr>";
            vResult += "        <th scope='row'>pH Tanaman</th>";
            vResult += "        <td>:" + bindings.getJSONObject(0).getJSONObject("RangepH").getString("value") + "</td>";
            vResult += "    </tr>";
            vResult += "    <tr>";
            vResult += "       <th scope='row'>Tanaman Daerah</th>";
            vResult += "        <td>:" + bindings.getJSONObject(0).getJSONObject("daerahTanaman").getString("value") + "</td>";
            vResult += "  </tr>";
            vResult += "   <tr>";
            vResult += "      <th scope='row'>Etnis Pengguna</th>";
            vResult += "        <td>:" + bindings.getJSONObject(0).getJSONObject("etnis").getString("value") + "</td>";
            vResult += "   </tr>";
            vResult += "  </tbody>";
            vResult += "  </table>";
            vResult += "  </div>";
            vResult += " </div>";
            vResult += " <div class='row'>";
            vResult += "    <div class='col-md-12'>";
            vResult += "      <table class='table table-bordered'>";
            vResult += "       <thead>";
            vResult += "         <tr>";
            vResult += "            <th scope='col'>Bagian yang digunakan</th>";
            vResult += "           <th scope='col'>Manfaat</th>";
            vResult += "           <th scope='col'>Cara Pengguanaan</th>";
            vResult += "       </tr>";
            vResult += "     </thead>";
            vResult += "     <tbody>";
            for (int i = 0; i < bindings.length(); i++) {
                vResult += "<tr>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("bagianTanaman").getString("value") + "</td>";
                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("manfaat").getString("value") + "</td>";
                vResult += " <td>" + bindings.getJSONObject(i).getJSONObject("penggunaan").getString("value") + "</td>";
                vResult += "</tr>";

            }
            vResult += " </tbody>";
            vResult += "  </table>";
            vResult += "  </div>";
            vResult += "</div>";
            vResult += "</ul></td></tr></table>";
            vResult += "  </div>";
            vResult += "</div>";
        }

        return vResult;
    }

    static String showManfaat(ResultSet rs, int dataFound) throws JSONException {
        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        int posisi = 0;
        int no = 0;
//        rs.hasNext();
//        QuerySolution rz = rs.nextSolution();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String json = new String(outputStream.toByteArray());
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsonArray = jsnobject.getJSONObject("results");
        JSONArray bindings = jsonArray.getJSONArray("bindings");
        vResult += "<div class='row'>";
        vResult += " <div class='col-md-5'>";
        vResult += " <table class='table  table-borderless table-condensed table-hover'>";
        vResult += "     <thead>";
        vResult += "  <tr>";
        vResult += "    <th colspan='2' scope='col'>HASIL OUTPUT :</th>";
        vResult += "  </tr>";
        vResult += " </thead>";
        vResult += " <tbody>";
        vResult += " <tr>";
        vResult += "    <th scope='row'>Tanaman</th>";
        vResult += "    <td>:";

        vResult += bindings.getJSONObject(0).getJSONObject("namaTanaman").getString("value") + "";

        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  </tbody>";
        vResult += "  </table>";
        vResult += " </div>";
        vResult += "</div>";

        if (bindings.length() > 0) {
            vResult += "    <div class='col-md-5'>";
            vResult += "      <table class='table table-bordered'>";
            vResult += "       <thead>";
            vResult += "         <tr>";

            vResult += "            <th scope='col'>Manfaat</th>";
            vResult += "       </tr>";
            vResult += "     </thead>";
            vResult += "     <tbody>";

            for (int i = 0; i < bindings.length(); i++) {
                vResult += "<tr>";

                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("namaManfaat").getString("value") + "</td>";
                vResult += "</tr>";

            }
            vResult += " </tbody>";
            vResult += "  </table>";
            vResult += "  </div>";
            vResult += "</div>";
            vResult += "</ul></td></tr></table>";
            vResult += "  </div>";
            vResult += "</div>";
        }
        return vResult;
    }

    static String showObat(ResultSet rs, int dataFound) throws JSONException {

        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        int posisi = 0;
        int no = 0;
//        rs.hasNext();
//        QuerySolution rz = rs.nextSolution();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String json = new String(outputStream.toByteArray());
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsonArray = jsnobject.getJSONObject("results");
        JSONArray bindings = jsonArray.getJSONArray("bindings");
        vResult += "<div class='row'>";
        vResult += " <div class='col-md-5'>";
        vResult += " <table class='table  table-borderless table-condensed table-hover'>";
        vResult += "     <thead>";
        vResult += "  <tr>";
        vResult += "    <th colspan='2' scope='col'>HASIL OUTPUT :</th>";
        vResult += "  </tr>";
        vResult += " </thead>";
        vResult += " <tbody>";
        vResult += " <tr>";
        vResult += "    <th scope='row'>Tanaman</th>";
        vResult += "    <td>:";
        if (sentence.get(1).equalsIgnoreCase("namaTanaman")) {
            vResult += bindings.getJSONObject(0).getJSONObject("namaTanaman").getString("value") + "";
        } else if (sentence.get(1).equalsIgnoreCase("namaBagianTanaman")) {
            vResult += bindings.getJSONObject(0).getJSONObject("namaBagianTanaman").getString("value") + "";
        }

        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  </tbody>";
        vResult += "  </table>";
        vResult += " </div>";
        vResult += "</div>";

        if (bindings.length() > 0) {
            vResult += "    <div class='col-md-5'>";
            vResult += "      <table class='table table-bordered'>";
            vResult += "       <thead>";
            vResult += "         <tr>";

            vResult += "            <th scope='col'>Penyakit</th>";
            vResult += "       </tr>";
            vResult += "     </thead>";
            vResult += "     <tbody>";

            for (int i = 0; i < bindings.length(); i++) {
                vResult += "<tr>";

                vResult += "<td>" + bindings.getJSONObject(i).getJSONObject("namaManfaat").getString("value") + "</td>";
                vResult += "</tr>";

            }
            vResult += " </tbody>";
            vResult += "  </table>";
            vResult += "  </div>";
            vResult += "</div>";
            vResult += "</ul></td></tr></table>";
            vResult += "  </div>";
            vResult += "</div>";
        }

        return vResult;

    }

    static String showHabitat(ResultSet rs, int dataFound) {
        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        rs.hasNext();
        QuerySolution rz = rs.nextSolution();
        vResult += "<div class='row'>";
        vResult += " <div class='col-md-5'>";
        vResult += " <table class='table  table-borderless table-condensed table-hover'>";
        vResult += "     <thead>";
        vResult += "  <tr>";
        vResult += "    <th colspan='2' scope='col'>HASIL OUTPUT :</th>";
        vResult += "  </tr>";
        vResult += " </thead>";
        vResult += " <tbody>";
        vResult += " <tr>";
        vResult += "    <th scope='row'>Nama Tanaman</th>";
        vResult += "    <td>:";
        vResult += rz.getLiteral("namaTanaman").getValue().toString() + "";
        vResult += "</td>";
        vResult += "  </tr>";
        vResult += "  <tr>";
        vResult += "       <th scope='row'>Nama Latin</th>";
        vResult += "        <td>:";
        vResult += rz.getLiteral("namaLatin").getValue().toString() + "";
        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  <tr>";
        vResult += "       <th scope='row'>Habitat </th>";
        vResult += "        <td>:";
        vResult += rz.getLiteral("namaHabitat").getValue().toString() + "";
        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  <tr>";
        vResult += "       <th scope='row'>Habitus </th>";
        vResult += "        <td>:";
        vResult += rz.getLiteral("namaHabitus").getValue().toString() + "";
        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  </tbody>";
        vResult += "  </table>";
        vResult += " </div>";
        vResult += "</div>";

        return vResult;
    }

    static String showPenggunaan(ResultSet rs, int dataFound) throws JSONException {
        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        rs.hasNext();
        String ManfaatName = "";
        String idManfaat = "";
        int posisi = 0;
        int no = 0;
        
        
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String json = new String(outputStream.toByteArray());
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsonArray = jsnobject.getJSONObject("results");
        JSONArray bindings = jsonArray.getJSONArray("bindings");
           vResult += "<div class='row'>";
        vResult += " <div class='col-md-5'>";
        vResult += " <table class='table  table-borderless table-condensed table-hover'>";
        vResult += "     <thead>";
        vResult += "  <tr>";
        vResult += "    <th colspan='2' scope='col'>HASIL OUTPUT :</th>";
        vResult += "  </tr>";
        vResult += " </thead>";
        vResult += " <tbody>";
        vResult += " <tr>";
        vResult += "    <th scope='row'>Nama Tanaman</th>";
        vResult += "    <td>:";
        vResult += bindings.getJSONObject(0).getJSONObject("namaTanaman").getString("value");
        vResult += "</td>";
        vResult += "  </tr>";
        vResult += "  <tr>";
        vResult += "       <th scope='row'>Nama Latin</th>";
        vResult += "        <td>:";
       vResult += bindings.getJSONObject(0).getJSONObject("namaLatin").getString("value");
        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  </tbody>";
        vResult += "  </table>";
        vResult += " </div>";
        vResult += "</div>";
        
        vResult += " <div class='row'>";
        vResult += "    <div class='col-md-12'>";
        vResult += "      <table class='table table-bordered'>";
        vResult += "       <thead>";
        vResult += "         <tr>";
        vResult += "            <th scope='col'>Cara Penggunaan</th>";
        vResult += "           <th scope='col'>Bagian Tanaman</th>";
 vResult += "           <th scope='col'>Manfaat</th>";
        vResult += "       </tr>";
        vResult += "     </thead>";
        vResult += "     <tbody>";

        for (int i = 0; i < bindings.length(); i++) {
            vResult += "         <tr>";
            vResult += "     <th scope='row'>" + bindings.getJSONObject(i).getJSONObject("namaCaraPenggunaan").getString("value") + "</th>";
            vResult += "     <th scope='row'>" + bindings.getJSONObject(i).getJSONObject("namaBagianTanaman").getString("value") + "</th>";
             vResult += "     <th scope='row'>" + bindings.getJSONObject(i).getJSONObject("namaManfaat").getString("value") + "</th>";
            vResult += "   </tr>";
            posisi++;
        }
        vResult += " </tbody>";
        vResult += "  </table>";
        vResult += "  </div>";
        vResult += "</div>";
        vResult += "  <br>";
        
        
        return vResult;
    }

    static String showKlasifikasi(ResultSet rs, int dataFound) {
        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        rs.hasNext();
        String ManfaatName = "";
        String idManfaat = "";
        int posisi = 0;
        int no = 0;
        QuerySolution rz = rs.nextSolution();
        vResult += "<div class='row'>";
        vResult += " <div class='col-md-5'>";
        vResult += " <table class='table  table-borderless table-condensed table-hover'>";
        vResult += "     <thead>";
        vResult += "  <tr>";
        vResult += "    <th colspan='2' scope='col'>HASIL OUTPUT :</th>";
        vResult += "  </tr>";
        vResult += " </thead>";
        vResult += " <tbody>";
        vResult += " <tr>";
        vResult += "    <th scope='row'>Nama Tanaman</th>";
        vResult += "    <td>:";
        vResult += rz.getLiteral("namaTanaman").getValue().toString() + "";
        vResult += "</td>";
        vResult += "  </tr>";
        vResult += "  <tr>";
        vResult += "       <th scope='row'>Nama Latin</th>";
        vResult += "        <td>:";
        vResult += rz.getLiteral("namaLatin").getValue().toString() + "";
        vResult += "</td>";
        vResult += "     </tr>";
        vResult += "  </tbody>";
        vResult += "  </table>";
        vResult += " </div>";
        vResult += "</div>";
        vResult += " <div class='row'>";
        vResult += "    <div class='col-md-12'>";
        vResult += "      <table class='table table-bordered'>";
        vResult += "       <thead>";
        vResult += "         <tr>";
        vResult += "            <th scope='col'>Tingkat Klasifikasi</th>";
        vResult += "           <th scope='col'>Nama Klasifikasi</th>";
        vResult += "       </tr>";
        vResult += "     </thead>";
        vResult += "     <tbody>";
        vResult += "         <tr>";
        vResult += "     <th scope='row'>" + rz.getLiteral("tingkatKlasifikasi").getValue().toString() + "</th>";
        vResult += "            <td>" + rz.getLiteral("namaKlasifikasi").getValue().toString() + "</td>";
        vResult += "   </tr>";

        while (rs.hasNext()) {
            QuerySolution qs = rs.nextSolution();
            idManfaat = qs.getLiteral("namaTanaman").getValue().toString();

            vResult += "         <tr>";
            vResult += "     <td>" + qs.getLiteral("tingkatKlasifikasi").getValue().toString() + "</td>";

            vResult += "            <td>" + qs.getLiteral("namaKlasifikasi").getValue().toString() + "</td>";
            vResult += "   </tr>";
            posisi++;

        }

        vResult += " </tbody>";
        vResult += "  </table>";
        vResult += "  </div>";
        vResult += "</div>";
        vResult += "  <br>";
        return vResult;
    }

    static String showEtnis(ResultSet rs, int dataFound) throws JSONException {
        String vResult = "<div id='found'>Ditemukan " + Integer.toString(dataFound) + " data ( " + showTime() + " ms )</div>";
        rs.hasNext();
        int posisi = 0;
        int no = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String json = new String(outputStream.toByteArray());
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsonArray = jsnobject.getJSONObject("results");
        JSONArray bindings = jsonArray.getJSONArray("bindings");

        vResult += " <div class='row'>";
        vResult += "    <div class='col-md-12'>";
        vResult += "      <table class='table table-bordered'>";
        vResult += "       <thead>";
        vResult += "         <tr>";
        vResult += "            <th scope='col'>Nama Etnis</th>";
        vResult += "           <th scope='col'>Asal Etnis</th>";

        vResult += "       </tr>";
        vResult += "     </thead>";
        vResult += "     <tbody>";

        for (int i = 0; i < bindings.length(); i++) {
            vResult += "         <tr>";
            vResult += "     <th scope='row'>" + bindings.getJSONObject(i).getJSONObject("namaEtnis").getString("value") + "</th>";
            vResult += "     <th scope='row'>" + bindings.getJSONObject(i).getJSONObject("namaDaerah").getString("value") + "</th>";
            vResult += "   </tr>";
            posisi++;
        }
        vResult += " </tbody>";
        vResult += "  </table>";
        vResult += "  </div>";
        vResult += "</div>";
        vResult += "  <br>";
        return vResult;
    }

    public static String tes_showResult() throws JSONException {
        String vResult = "Ini TES<br/><table border='1'>";
        String query = "";
        query = ""
                + "SELECT DISTINCT ?s  ?bagianTanamanYgDigunakanEtnis "
                + "WHERE { "
                //                        + "?s <http://example.org/ethnomed#hasPengguna> ?e ."
                //                        + "?e <http://example.org/ethnomed#namaEtnis> ?namaEtnis ."
                + "?s <http://example.org/ethnomed#bagianTanamanYgDigunakanEtnis> ?bagianTanamanYgDigunakanEtnis ."
                //                        
                + "FILTER (?bagianTanamanYgDigunakanEtnis = 'daun sirih') "
                + "}";

        ResultSet rs = Library.runQuery(query);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String json = new String(outputStream.toByteArray());
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsonArray = jsnobject.getJSONObject("results");
        JSONArray bindings = jsonArray.getJSONArray("bindings");
        vResult += bindings;

        return vResult;

    }

    public static String showQuery() {
        return Library.createQuery();
    }

    public static String showMeaning() {
        String res = "Data " + sentence.get(0) + " " + Library.theClassMeaning;

//        for (int i = 0; i < Library.aPattern.length; i++) {
//            String meaning = Library.aPattern[i][2];
//            
////            if(Library.theClass.equalsIgnoreCase("tanaman")){
////           if (Library.sword.get(1).equals("obat") || Library.sword.get(1).equals("penyakit") || Library.sword.get(1).equals("sembuh")) {
////                meaning = "untuk mengobati penyakit";
////            } else if (Library.sword.get(1).equals("buat") || Library.sword.get(1).equals("guna")) {
////                meaning = " dimanfaatkan untuk";
////            } 
////            
////            else if (Library.sword.get(3).equals("sebar")) {
////               meaning = Library.aPattern[i][2];
////            } 
////            
////            else {
////                meaning = Library.aPattern[i][2];h
////            }
////            }
////            else {
////               meaning = Library.aPattern[i][2];
////            }
//          
//             res +=  " " +  Library.aPattern[i][3] + " " + meaning + " " + Library.aPattern[i][1];
//        }
        for (String key : Library.meaning) {
            res += key + " ";
        }

        return res;
    }
}
