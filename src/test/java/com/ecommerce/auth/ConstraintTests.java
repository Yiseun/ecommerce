package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.Constraint;
import com.ecommerce.auth.exception.application.domain.BusinessLogicException;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import org.junit.jupiter.api.Test;

public class ConstraintTests {

    @Test
    void 매개변수가_비어있다면_실패한다(){
        final Constraint constraint = Constraint.from(null);
        final Constraint request = null;

        assertThatThrownBy(()->constraint.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 자신의_tryCount가_null이라면_실패한다(){
        final Constraint constraint = Constraint.createEmpty();
        final Constraint request = Constraint.from(null);

        assertThatThrownBy(()->constraint.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 자신의_tryCount가_다_떨어졌다면_실패한다(){
        final Constraint constraint = Constraint.from(-1);
        final Constraint request = Constraint.createEmpty();

        assertThatThrownBy(()->constraint.update(request)).isInstanceOf(BusinessLogicException.class);
    }
}
