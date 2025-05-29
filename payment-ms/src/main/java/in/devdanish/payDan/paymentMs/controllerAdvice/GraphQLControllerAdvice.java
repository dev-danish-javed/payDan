package in.devdanish.payDan.paymentMs.controllerAdvice;

import graphql.GraphQLError;
import in.devdanish.payDan.paymentMs.exception.MissingWalletException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphQLControllerAdvice {
    @GraphQlExceptionHandler(MissingWalletException.class)
    public GraphQLError helloji(MissingWalletException e) {
        return e;
    }
}
