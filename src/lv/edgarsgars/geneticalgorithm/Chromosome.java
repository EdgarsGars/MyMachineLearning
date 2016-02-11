package lv.edgarsgars.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;

public class Chromosome<E> implements Comparable<Chromosome<E>> {

    public List<E> genes;
    public Class<E> geneClass;
    public double fitness;

    public Chromosome(int geneSize, Class<E> geneClass) {
        this.geneClass = geneClass;
        init(geneSize);
    }

    @Override
    public int compareTo(Chromosome<E> o) {
        if (o.fitness - fitness > 0) {
            return -1;
        } else if (o.fitness - fitness < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void init(List<E> genes) {
        this.genes = new ArrayList<>(genes.size());
        for (int i = 0; i < genes.size(); i++) {
            Gene g = (Gene) (genes.get(i));
            this.genes.add((E) (g.copy()));
        }
        //  System.out.println("=>" + genes.size());
    }

    protected void init(int geneCount) {
        genes = new ArrayList<E>();
        for (int i = 0; i < geneCount; i++) {
            try {
                genes.add(geneClass.newInstance());
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String toString() {
        String s = "[";
        for (E gene : genes) {
            s += gene + ",";
        }
        // s = s.substring(0, s.length() - 2);
        s += "]";
        return s;
    }
}
