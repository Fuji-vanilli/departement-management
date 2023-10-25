package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Departement {
    private String nom;
    private String codePostale;
    private List<Commune> communes= new ArrayList<>();

    public Departement() {}

    public Departement(String nom, String codePostale) {
        this.nom = nom;
        this.codePostale = codePostale;
    }
    public void addCommune(Commune commune) {
        this.communes.add(commune);
    }
    public List<Commune> getCommunes(){
        return Collections.unmodifiableList(communes);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departement that)) return false;
        return Objects.equals(getNom(), that.getNom()) && Objects.equals(getCodePostale(), that.getCodePostale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNom(), getCodePostale());
    }

    @Override
    public String toString() {
        return "Departement{" +
                "nom='" + nom + '\'' +
                ", codePostale='" + codePostale + '\'' +
                '}';
    }
}
