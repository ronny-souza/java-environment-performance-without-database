package br.com.rmsa.benchmark.model.transport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MetadataDTOTest {

    @Test
    @DisplayName("Checking whether the fields were filled out correctly")
    void checkingWhetherTheFieldsWereFilledOutCorrectly() {
        MetadataDTO metadataDTO = getMetadataDTO();

        Assertions.assertNotNull(metadataDTO.getFirstname());
        Assertions.assertNotNull(metadataDTO.getLastname());
        Assertions.assertNotNull(metadataDTO.getEmail());
        Assertions.assertNotNull(metadataDTO.getAge());
        Assertions.assertNotNull(metadataDTO.getGender());
        Assertions.assertNotNull(metadataDTO.getPhone());
        Assertions.assertNotNull(metadataDTO.getAddress());
        Assertions.assertNotNull(metadataDTO.getCity());
        Assertions.assertNotNull(metadataDTO.getState());
        Assertions.assertNotNull(metadataDTO.getZipCode());
        Assertions.assertNotNull(metadataDTO.getCountry());
        Assertions.assertNotNull(metadataDTO.getJob());

    }

    private static MetadataDTO getMetadataDTO() {
        MetadataDTO metadataDTO = new MetadataDTO();
        metadataDTO.setFirstname("Firstname");
        metadataDTO.setLastname("Lastname");
        metadataDTO.setEmail("email@example.com");
        metadataDTO.setAge(23);
        metadataDTO.setGender("Male");
        metadataDTO.setPhone("(99) 99999-9999");
        metadataDTO.setAddress("Address");
        metadataDTO.setCity("City");
        metadataDTO.setState("State");
        metadataDTO.setZipCode("ZipCode");
        metadataDTO.setCountry("Country");
        metadataDTO.setJob("Job");
        return metadataDTO;
    }

}