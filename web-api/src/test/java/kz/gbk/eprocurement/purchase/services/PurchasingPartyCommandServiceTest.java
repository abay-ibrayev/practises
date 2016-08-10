package kz.gbk.eprocurement.purchase.services;

import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import kz.gbk.eprocurement.purchase.model.PurchasingPartyNotFoundException;
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PurchasingPartyCommandServiceTest {

    @Mock
    private PurchasingPartyRepository repository;

    private PurchasingPartyCommandService cmdService;

    @Before
    public void setUp() throws Exception {
        cmdService = new PurchasingPartyCommandService(repository);
    }

    @Test
    public void shouldAddChildParties() throws Exception {
        PurchasingParty parent = new PurchasingParty();
        parent.setId(1L);

        PurchasingParty child = new PurchasingParty();
        child.setShortName("AJAX");
        child.setFullName("Asynchronous JavaScript and XML JSC");

        when(repository.findOne(eq(parent.getId()))).thenReturn(parent);

        cmdService.addChildrenParties(parent.getId(), Collections.singletonList(child));

        verify(repository, times(1)).save(
                (PurchasingParty) argThat(hasProperty("shortName", equalTo(child.getShortName()))));
    }

    @Test(expected = PurchasingPartyNotFoundException.class)
    public void shouldFailWithExceptionIfParentPartyNotFound() {
        cmdService.addChildrenParties(1L, Collections.singletonList(new PurchasingParty()));
    }

}