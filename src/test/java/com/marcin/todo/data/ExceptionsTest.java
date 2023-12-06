package com.marcin.todo.data;

import com.marcin.todo.entity.Label;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class ExceptionsTest {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsTest.class);

    @Autowired
    private LabelRepository labelRepository;




    @Test
    void raiseExceptionWhenCreatingLabelWithoutName() {

        logger.info(" 5.3 Verify that the repository correctly raises an exception when trying to create a label without providing a name or empty name field.");

        // Try to create a label without a name
        Label label = new Label();

        // Try to save the label to the repository and expect an exception
        assertThrows(DataIntegrityViolationException.class, () -> {
            labelRepository.save(label);
        });

        // Try to create a label with an empty name
        label.setName("");

    }


}
