package digestionentity;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class DataEnrichment {
    public static String enrichData(String data) {
        String[] s;
        StringBuilder sb = new StringBuilder();
        s = data.split(" ");
        for (String parameter : s) {
            if (parameter.matches("00|01|02"))
                sb.append("XX-YY-").append(String.format("%02d", Integer.parseInt(s[0]))).append(" ");
            sb.append(parameter).append(" ");
        }
        if (s[2].equals("01"))
            sb.append("100");
        return sb.toString().trim();
    }
}
