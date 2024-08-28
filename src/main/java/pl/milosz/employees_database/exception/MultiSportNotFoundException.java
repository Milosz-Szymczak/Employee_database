package pl.milosz.employees_database.exception;

public class MultiSportNotFoundException extends RuntimeException{
    public MultiSportNotFoundException(Long id){
        super("MultiSport not found with id: " + id);
    }
}
