package com.sparkystudios.traklibrary.game.repository.specification;

import com.sparkystudios.traklibrary.game.domain.Platform;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.StartingWithIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "id", spec = Equal.class),
        @Spec(path = "name", spec = StartingWithIgnoreCase.class)
})
public interface PlatformSpecification extends Specification<Platform> {
}
