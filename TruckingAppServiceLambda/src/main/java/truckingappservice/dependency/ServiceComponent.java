package truckingappservice.dependency;

import dagger.Component;
import truckingappservice.activity.CreateExpenseActivity;
import truckingappservice.activity.CreateProfileActivity;
import truckingappservice.activity.GetAllExpensesActivity;
import truckingappservice.activity.UpdateProfileActivity;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return AddSongToPlaylistActivity
     */

    GetAllExpensesActivity provideGetAllExpensesActivity();

    CreateProfileActivity provideCreateProfileActivity();

    UpdateProfileActivity provideUpdateProfileActivity();

    CreateExpenseActivity provideCreateExpenseActivity();

}