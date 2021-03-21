import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        //examples of program execution
        System.out.println(unpackString("3[xyz]4[xy]z").equals("xyzxyzxyzxyxyxyxyz"));
        System.out.println(unpackString("2[3[x]y]").equals("xxxyxxxy"));
        System.out.println(unpackString("xy2[ab]").equals("xyabab"));
        System.out.println(unpackString("2[3[2[a]x]y]").equals("aaxaaxaaxyaaxaaxaaxy"));
        System.out.println(unpackString("10[a]").equals("aaaaaaaaaa"));
        System.out.println(unpackString("2[fa3[ac]er]").equals("faacacacerfaacacacer"));
    }

    private static int posCounterForString = 0;

    public static void setPosCounterForString(int pos) {
        posCounterForString = pos;
    }

    public static int getPosCounterForString() {
        return posCounterForString;
    }

    private static int posCounterForSubString = 0;

    public static int getPosCounterForSubString() {
        return posCounterForSubString;
    }

    public static void setPosCounterForSubString(int posCounterForSubString) {
        Main.posCounterForSubString = posCounterForSubString;
    }

    public static String unpackString(String inputString) {
        StringBuilder answer = new StringBuilder();
        char[] tempCharArray = inputString.toCharArray();
        for (int i = 0; i < inputString.length(); i++) {
            if (Character.isDigit(tempCharArray[i])) {
                String numberRepeat = getCountOfRepeat(inputString.substring(i));
                int countOfRepeat = Integer.parseInt(numberRepeat);
                i += numberRepeat.length();
                answer.append(unpackSubString(countOfRepeat, inputString.substring(i)));
                i += getPosCounterForString();
            } else {
                answer.append(tempCharArray[i]);
            }
        }
        return answer.toString();
    }

    private static String unpackSubString(int countOfRepeat, String subString) {
        String answer;
        StringBuilder frame = new StringBuilder();
        char[] subCharArray = subString.toCharArray();
        for (int i = 0; i < subString.length(); i++) {
            if (subCharArray[i] == '[') {
                for (int j = ++i; j < subString.length(); j++) {
                    if (subCharArray[j] == ']') {
                        setPosCounterForString(j);
                        break;
                    }
                    if (Character.isDigit(subCharArray[j])) {
                        String numberRepeat = getCountOfRepeat(subString.substring(j));
                        int num = Integer.parseInt(numberRepeat);
                        j += numberRepeat.length();
                        frame.append(unpackNestedString(num, subString.substring(j)));
                        j += getPosCounterForSubString();
                        continue;
                    }
                    frame.append(subCharArray[j]);
                }
                break;
            }
        }
        String finalFrame = frame.toString();
        answer = IntStream
                .range(0, countOfRepeat)
                .mapToObj(i -> finalFrame)
                .collect(Collectors.joining(""));
        return answer;
    }

    private static String unpackNestedString(int num, String nestedString) {
        String answer;
        StringBuilder frame = new StringBuilder();
        char[] subCharArray = nestedString.toCharArray();
        for (int i = 0; i < nestedString.length(); i++) {
            if (subCharArray[i] == '[') {
                for (int j = ++i; j < nestedString.length(); j++) {
                    if (subCharArray[j] == ']') {
                        setPosCounterForSubString(j);
                        break;
                    }
                    if (Character.isDigit(subCharArray[j])) {
                        String numberRepeat = getCountOfRepeat(nestedString.substring(j));
                        int numForRepeat = Integer.parseInt(numberRepeat);
                        j += numberRepeat.length();
                        frame.append(unpackNestedString(numForRepeat, nestedString.substring(j)));
                        j += getPosCounterForSubString();
                        continue;
                    }
                    frame.append(subCharArray[j]);
                }
                break;
            }
        }
        String finalFrame = frame.toString();
        answer = IntStream
                .range(0, num)
                .mapToObj(i -> finalFrame)
                .collect(Collectors.joining(""));
        return answer;
    }

    private static String getCountOfRepeat(String str) {
        char[] subCharArray = str.toCharArray();
        String numberRepeat = "";
        for (int k = 0; k < str.length(); k++) {
            if (Character.isDigit(subCharArray[k])) {
                numberRepeat += Character.toString(subCharArray[k]);
            } else {
                break;
            }
        }
        return numberRepeat;
    }
}
