package org.example;

import java.util.Objects;

public class Commune {
    private String name;
    private String codePostale;

    public Commune() {}

    public Commune(String name, String codePostale) {
        this.name = name;
        this.codePostale = codePostale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof Commune commune)) return false;
        return Objects.equals(getName(), commune.getName()) && Objects.equals(getCodePostale(), commune.getCodePostale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCodePostale());
    }

    @Override
    public String toString() {
        return "Commune{" +
                "name='" + name + '\'' +
                ", codePostale='" + codePostale + '\'' +
                '}';
    }
}
