package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PlayWithDepartement {
    public static void main(String[] args) {
        Path pathDepartement = Paths.get("D:\\IXML\\departement.txt");
        Path pathCommune = Paths.get("D:\\IXML\\commune.txt");

        getDepartements(pathDepartement).forEach(System.out::println);

    }
    public static List<Departement> getDepartements(Path path) {
        Predicate<String> isComment= l-> l.startsWith("#");
        Function<String, String> getName= l-> l.substring(0, l.indexOf(" "));
        Function<String, String> getCodePostale= l-> l.substring(l.indexOf(" ")+3);
        Function<String, Departement> mapToDepartement= l-> new Departement(getName.apply(l), getCodePostale.apply(l));

        final List<Departement> departements;

        try {
            departements= Files.lines(path)
                    .filter(isComment.negate())
                    .map(mapToDepartement)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return departements;
    }
}
