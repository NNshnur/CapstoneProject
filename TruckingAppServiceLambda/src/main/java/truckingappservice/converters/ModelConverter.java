package truckingappservice.converters;

import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.models.ExpenseModel;
import truckingappservice.models.IncomeModel;
import truckingappservice.models.ProfileModel;

public class ModelConverter {




    public ExpenseModel toExpenseModel(Expense expense) {
        return ExpenseModel.builder()
                .withCategory(expense.getCategory())
                .withAmount(expense.getAmount())
                .withExpenseId(expense.getExpenseId())
                .withDate(expense.getDate())
                .withPaymentType(expense.getPaymentType())
                .withTruckId(expense.getTruckId())
                .withVendorName(expense.getVendorName())
                .build();
    }

    public ProfileModel toProfileModel(Profile profile) {
        return ProfileModel.builder()
                .withProfileId(profile.getId())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withCompanyName(profile.getCompanyName())
                .withTruckId(profile.getTruckId())
                .withStartingBalance(profile.getStartingBalance())
                .build();
    }

    public IncomeModel toIncomeModel(Income income) {
        return IncomeModel.builder()
                .withIncomeId(income.getIncomeId())
                .withTruckId(income.getTruckId())
                .withDate(income.getDate())
                .withDeadHeadMiles(income.getDeadHeadMiles())
                .withLoadedMiles(income.getLoadedMiles())
                .withTotalMiles(income.getTotalMiles())
                .withGrossIncome(income.getGrossIncome())
                .withRatePerMile(income.getRatePerMile())
                .build();
    }
}

