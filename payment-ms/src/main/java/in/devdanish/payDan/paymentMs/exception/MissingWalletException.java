package in.devdanish.payDan.paymentMs.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class MissingWalletException extends RuntimeException implements GraphQLError {

    private final String message;
    private final List<SourceLocation> locations;
    private final ErrorType errorType;

    public MissingWalletException(String walletId, List<SourceLocation> locations, ErrorType errorType) {
        this.message = "Wallet not found for walletId: " + walletId;
        this.locations = locations;
        this.errorType = errorType;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public List<SourceLocation> getLocations() {
       return this.locations;
    }

    @Override
    public ErrorClassification getErrorType() {
        return this.errorType;
    }
}
