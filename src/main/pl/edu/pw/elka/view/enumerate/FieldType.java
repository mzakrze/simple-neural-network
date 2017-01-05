package pl.edu.pw.elka.view.enumerate;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;

public enum FieldType {
    BLACK(Pair.of(1,0)), WHITE(Pair.of(0,1)), NONE(Pair.of(0,0));

    @Getter
    private Pair<Integer,Integer> vector;

    FieldType(Pair<Integer, Integer> vector){
        this.vector = vector;
    }

    public static FieldType getFieldTypeForVector(Pair<Double, Double> vector){
        if(vector.getLeft() == 1 && vector.getRight() == 0)
            return FieldType.BLACK;
        else if(vector.getLeft() == 0 && vector.getRight() == 1)
            return FieldType.WHITE;
        else if(vector.getLeft() == 0 && vector.getRight() == 0)
            return FieldType.NONE;
        else
            throw new IllegalArgumentException(); // TODO zastanowic sie nad typem wyjatku
    }



//    public static FieldType getFieldTypeForVector(Pair<Double, Double> doubleDoublePair) {
//        return getFieldTypeForVector(Pair.of(doubleDoublePair.getLeft().intValue(), doubleDoublePair.getRight().intValue()));
//    }
}
