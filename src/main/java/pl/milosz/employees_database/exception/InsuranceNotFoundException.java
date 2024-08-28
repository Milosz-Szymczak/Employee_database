package pl.milosz.employees_database.exception;

public class InsuranceNotFoundException extends RuntimeException{
    public InsuranceNotFoundException(Long id){
        super("Insurance Not Found with id: " + id);
    }
}
