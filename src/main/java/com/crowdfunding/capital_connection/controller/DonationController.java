package com.crowdfunding.capital_connection.controller;

import com.crowdfunding.capital_connection.controller.dto.DonationRequest;
import com.crowdfunding.capital_connection.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    /**
     * Endpoint para crear una nueva donación.
     *
     * @param donationRequest  Información de la donación.
     * @return La donación creada como respuesta.
     */
    @PostMapping
    public ResponseEntity<DonationRequest> createDonation(
            @RequestBody DonationRequest donationRequest) {
        DonationRequest createdDonation = donationService.createDonation(donationRequest);
        return ResponseEntity.ok(createdDonation);
    }

    /**
     * Endpoint para obtener todas las donaciones de un usuario.
     *
     * @param accountId ID del usuario.
     * @return Lista de donaciones realizadas por el usuario.
     */
    @GetMapping
    public ResponseEntity<List<DonationRequest>> getDonationsByAccountId(@PathVariable Long accountId) {
        List<DonationRequest> donations = donationService.getDonationsByAccountId(accountId);
        return ResponseEntity.ok(donations);
    }
}
