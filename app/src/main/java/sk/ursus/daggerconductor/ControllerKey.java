package sk.ursus.daggerconductor;

import com.bluelinelabs.conductor.Controller;
import dagger.MapKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 21.12.2018.
 */
@MapKey
@Target({ ElementType.METHOD }) public @interface ControllerKey {
    Class<? extends Controller> value();
}
