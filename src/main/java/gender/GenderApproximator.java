package gender;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Basic class to induce gender based on the first name.
 * Not a reliable method, obviously, but covers more cases than randomly guessing.
 */
public class GenderApproximator {

    private final Gson gson = new Gson();

    public Gender approximateGender(String firstName, String lastName) throws Exception {
        if (lastName == null || lastName.trim().equals("")) {
            lastName = "*";
        }
        String json = readUrl("https://api.namsor.com/onomastics/api/json/gendre/" + firstName + "/" + lastName);

        //convert the json string back to object
        GenderData obj = gson.fromJson(json, GenderData.class);

        return Gender.valueOf(obj.gender.toUpperCase());

    }

    private static class GenderData {
        private String gender;
    }


    // Read JSON
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }

    }
}
