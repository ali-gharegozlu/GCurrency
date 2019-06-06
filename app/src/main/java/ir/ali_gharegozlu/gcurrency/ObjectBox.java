package ir.ali_gharegozlu.gcurrency;

import android.content.Context;

import io.objectbox.BoxStore;
import ir.ali_gharegozlu.gcurrency.model.MyObjectBox;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {

            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();
    }

    public static BoxStore get() { return boxStore; }
}

