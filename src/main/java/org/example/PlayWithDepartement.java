package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayWithDepartement {
    public static void main(String[] args) {
        Path pathDepartement = Paths.get("D:\\IXML\\departement.txt");
        Path pathCommune = Paths.get("D:\\IXML\\commune.txt");

        List<Departement> departements= getDepartements(pathDepartement);
        List<Commune> communes= getCommune(pathCommune);

        Function<Commune, String> communeForCodeDep= c->
                c.getCodePostale().startsWith("97") ?
                        c.getCodePostale().substring(0, 3) :
                        c.getCodePostale().substring(0, 2);
        Function<Commune, String> toNoms= Commune::getName;

        final Map<String, List<Commune>> communeByCodeDepartement = communes.stream()
                .collect(Collectors.groupingBy(communeForCodeDep));

        final Map<String, Long> numberCommunesByDep = communes.stream()
                .collect(Collectors.groupingBy(communeForCodeDep, Collectors.counting()));

        Consumer<Departement> addCommuneToDepartement= d-> communes.stream()
                        .filter(c-> c.getCodePostale().startsWith(d.getCodePostale()))
                                .forEach(d::addCommune);

        departements.forEach(addCommuneToDepartement);
        Function<Departement, Stream<Commune>> flatMappingDepartement= d-> d.getCommunes().stream();

        final long totalCommune = departements.stream()
                .flatMap(flatMappingDepartement)
                .count();

        departements.forEach(
                d-> System.out.println("departement: "+d.getCodePostale()+" possede :"+d.getCommunes().size()+" communes")
        );
        numberCommunesByDep.forEach(
                (key, value)-> System.out.println(key+" : "+value)
        );

        System.out.println(totalCommune+" = "+communes.size());

    }
    public static List<Commune> getCommune(Path path) {
        Predicate<String> isComment= l-> l.startsWith("#");

        Function<String, String> getNom= l-> l.substring(0, l.indexOf(" ("));
        Function<String, String> getCodePostal= l-> l.substring(l.indexOf("(")+1, l.indexOf(")"));
        Function<String, Commune> mapToCommune= l-> new Commune(getNom.apply(l), getCodePostal.apply(l));

        List<Commune> communes;
        try {
            communes= Files.lines(path)
                    .filter(isComment.negate())
                    .map(mapToCommune)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return communes;
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
