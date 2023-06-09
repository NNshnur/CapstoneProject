package truckingappservice.dependency;

import dagger.Component;
import truckingappservice.activity.*;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {


    GetAllExpensesActivity provideGetAllExpensesActivity();

    CreateProfileActivity provideCreateProfileActivity();

    UpdateProfileActivity provideUpdateProfileActivity();

    CreateExpenseActivity provideCreateExpenseActivity();

    UpdateExpenseActivity provideUpdateExpenseActivity();

    DeleteExpenseActivity provideDeleteExpenseActivity();

    GetProfileActivity provideGetProfileActivity();

    GetAllIncomeActivity provideGetAllIncomeActivity();

}