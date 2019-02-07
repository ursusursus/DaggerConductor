package sk.ursus.daggerconductor;

import com.bluelinelabs.conductor.Controller;
import dagger.android.DispatchingAndroidInjector;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 21.12.2018.
 */
public interface HasControllerInjector {
    DispatchingAndroidInjector<Controller> controllerInjector();
}
