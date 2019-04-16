package uz.codic.ahmadtea.utils;



import java.util.regex.Pattern;

import dagger.Component;


public class CyrillicLatin {
    public static String cyrillicToLatin(String text){
        char[] abcCyr = {'а','б','в','г','д','ё','ж','з','и', 'й', 'к','л','м','н','п','р','с','т','у','ў','ф','х', 'ҳ', 'ц','ш','щ','ы','э','ю','я', 'қ', 'ғ', 'ь', 'ъ', 'е', 'ч'};
        String[] abcLat = {"a","b","v","g","d","yo","j","z","i", "y", "k","l","m","n","p","r","s","t","u","u","f","x", "h", "ts","sh","sch","","e","yu","ya", "q", "g", "", "", "e", "ch"};
        StringBuilder english = new StringBuilder();
        text = text.toLowerCase();
        outer:
        for (int i = 0; i < text.length(); i++) {
            for(int x = 0; x < abcCyr.length; x++ )
                if (text.charAt(i) == abcCyr[x]) {
                    english.append(abcLat[x]);
                    continue outer; // jump to next letter
                }
            // if no replacement made, use character as-is
            english.append(text.charAt(i));
        }
        return english.toString();
    }

    public static String latinToCyrillic(String text){
        text = text.toLowerCase();
        text = text.replaceAll("sh", "ш");
        text = text.replaceAll("yu", "ю");
        text = text.replaceAll("ya", "я");
        text = text.replaceAll("yo", "ё");
        text = text.replaceAll("ts", "ц");
        text = text.replaceAll("ch", "ч");
        text = text.replaceAll("o`", "ў");
        text = text.replaceAll("o\'", "ў");
        text = text.replaceAll("g`", "ғ");
        text = text.replaceAll("g\'", "ғ");
        String[] abcLat = {"a","b","v","g","d","j","z","i", "y","k","l","m","n", "o", "p","r","s","t","u","f", "x", "h","q", "e"};
        char[] abcCyr = {'а','б','в','г','д','ж','з','и', 'й','к','л','м','н', 'о','п','р','с','т','у','ф','х', 'ҳ', 'қ','е'};
        StringBuilder english = new StringBuilder();

        outer:
        for (int i = 0; i < text.length(); i++) {
            for(int x = 0; x < abcLat.length; x++ )
                if (text.charAt(i) == abcLat[x].charAt(0)) {
                    english.append(abcCyr[x]);
                    continue outer; // jump to next letter
                }
            // if no replacement made, use character as-is
            english.append(text.charAt(i));
        }
        return english.toString();
    }


    public static boolean isCyrillic(String text) {
        return Pattern.matches(".*\\p{InCyrillic}.*", text);
    }

    public static String getOtherVersion(String text) {
        if (isCyrillic(text))
            return cyrillicToLatin(text);
        else
            return latinToCyrillic(text);
    }
}
