package com.wordpong.tools.translate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class UpdatePropertyFiles {

        /**
         * @param args
         * @throws IOException 
         * @throws FileNotFoundException 
         */
        public static void main(String[] args) throws Exception {
                File[] listFiles = new File(".").listFiles();
                final String suffix = "_en.properties";
                String en = null;
                for(final File file:listFiles){
                        String name = file.getName();
                        if(name.endsWith(suffix)){
                                en = name;
                                break;
                        }
                }
                if(en == null){
                        return;
                }
                GoogleAPI.setHttpReferrer("http://code.google.com/p/i18n-translator/");

                String baseName = en.substring(0, en.length() - suffix.length());
                Properties source = new Properties();
                {
                        FileInputStream inStream = new FileInputStream(en);
                        source.load(inStream);
                        inStream.close();
                }
                final Pattern regex = Pattern.compile("^" + baseName + "_(.+)\\.properties$");
                Map<String, Properties> targets = new HashMap<String, Properties>();
                for(final File file:listFiles){
                        String name = file.getName();
                        if(name.equals(en)){
                                continue;
                        }
                        Matcher matcher = regex.matcher(name);
                        if(! matcher.matches()){
                                continue;
                        }
                        String lang = matcher.group(1);
                        Language language = getLanguage(lang);
                        if(language == null){
                                System.err.println("unknown language " + lang + " ignored");
                                continue;
                        }
                        Properties target = new Properties();
                        FileInputStream inStream = new FileInputStream(name);
                        target.load(inStream);
                        targets.put(lang, target);
                        inStream.close();
                }
                
                for(Entry<String, Properties> pair:targets.entrySet()){
                        Properties targetProperties = pair.getValue();
                        Enumeration<Object> keys = targetProperties.keys();
                        while(keys.hasMoreElements()){
                                String key = keys.nextElement().toString();
                                if(! source.containsKey(key)){
                                        targetProperties.remove(key);;
                                }
                        }
                }
                Enumeration<Object> keys = source.keys();
                while(keys.hasMoreElements()){
                        String key = keys.nextElement().toString();
                        String value = source.getProperty(key).replaceFirst("&([\\w])", "$1");
                        List<Language>languages = new LinkedList<Language>();
                        for(Entry<String, Properties> pair:targets.entrySet()){
                                Properties targetProperties = pair.getValue();
                                if(targetProperties.containsKey(key)){
                                        continue;
                                }
                                languages.add(getLanguage(pair.getKey()));
                        }
                        if(languages.isEmpty()){
                                for(Properties targetProperties:targets.values()){
                                        if(targetProperties.containsKey(key)){
                                                targetProperties.remove(key);
                                        }
                                }
                                continue;
                        }
                        Language[] array = new Language[languages.size()];
                        languages.toArray(array);
                        System.out.println(value);
                        String[] translations = null;
                        for(int i = 1; i < 10; i++)
                        {
                                try {
                                        translations = Translate.execute(value, Language.ENGLISH, array);
                                        break;
                                } catch (Exception e) {
                                        Thread.sleep(500);
                                }
                        }
                        if(translations == null){
                                System.err.println("can not translate value \"" + value + "\"");
                                for(Properties targetProperties:targets.values()){
                                        if(targetProperties.containsKey(key)){
                                                targetProperties.setProperty(key, value ); //"[translate me]"
                                        }
                                }
                                continue;
                        }
                        int i = 0;
                        for(Properties targetProperties:targets.values()){
                                if(targetProperties.containsKey(key)){
                                        targetProperties.remove(key);
                                        continue;
                                }
                                String translatedValue = translations[i++];
                                if(translatedValue.equals(value)){
                                        targetProperties.setProperty(key, translatedValue ); //+ "[translate me]"
                                        continue;
                                }
                                targetProperties.setProperty(key, translatedValue); //+ "[auto]"
                        }
                }
                for(Entry<String, Properties> pair:targets.entrySet()){
                        Properties outputProperties = pair.getValue();
                        if(outputProperties.isEmpty()){
                                continue;
                        }
                        String fileName = baseName + "_" + pair.getKey().toString() + ".properties";
                        File outputFile = new File(fileName);
                        FileOutputStream out = new FileOutputStream(outputFile, true);
                        PrintStream outLn = new PrintStream(out);
                        outLn.println();
                        outputProperties.store(outLn, "automatic translated values");
                        outLn.close();
                }
        }

        private static Language getLanguage(String lang) {
                Language language = Language.fromString(lang);
                if(language != null){
                        return language;
                }
                language = Language.fromString(lang.substring(0, 2));
                return language;
        }

}
