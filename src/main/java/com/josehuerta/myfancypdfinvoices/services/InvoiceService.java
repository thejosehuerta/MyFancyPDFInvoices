package com.josehuerta.myfancypdfinvoices.services;

import com.josehuerta.myfancypdfinvoices.model.Invoice;
import com.josehuerta.myfancypdfinvoices.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Component
public class InvoiceService {

    private final JdbcTemplate jdbcTemplate;

    // We will use constructor injection
    private final UserService userService;

    private final String cdnUrl;

    // Field injection
    //@Autowired // With this annotation, Spring will construct the UserService for us and inject the dependencies
    public InvoiceService(UserService userService, JdbcTemplate jdbcTemplate, @Value("${cdn.url}") String cdnUrl) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Invoice> findAll() {
        // The row mapper lets us return every returned SQL row into a Java object
        return jdbcTemplate.query("select id, user_id, pdf_url, amount from invoices", (resultSet, rowNum) -> {
            Invoice invoice = new Invoice();
            invoice.setId(resultSet.getObject("id").toString());
            invoice.setPdfUrl(resultSet.getString("pdf_url"));
            invoice.setUserId(resultSet.getString("user_id"));
            invoice.setAmount(resultSet.getInt("amount"));
            return invoice;

        });
    }

    @Transactional
    public Invoice create(String userId, Integer amount) {
        // Dummy URL
        String generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";

        /*
        In order to return generated primary keys, create a preparedStatement with the RETURN_GENERATED_KEYS set to true.
        After this, the JDBC driver can make the generated ids available via the keyHolder object.
        */

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into invoices (user_id, pdf_url, amount) values (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            // Set parameters on the preparedStatement (will replace the ?'s in the SQL)
            ps.setString(1, userId);
            ps.setString(2, generatedPdfUrl);
            ps.setInt(3, amount);
            return ps;
        }, keyHolder);

        // Return the auto-generated UUID PK from database.
        String uuid = !keyHolder.getKeys().isEmpty() ? ((UUID) keyHolder.getKeys().values().iterator().next()).toString()
                : null;

        // Create new Invoice object
        Invoice invoice = new Invoice();
        invoice.setId(uuid);
        invoice.setPdfUrl(generatedPdfUrl);
        invoice.setAmount(amount);
        invoice.setUserId(userId);
        return invoice;
    }

    /*
    if you want to make sure that your object, including all its dependencies is completely constructed,
    you will need to use @PostConstruct.
     */
    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
    }

    /*
    PreDestroy method gets called whenever you shut down your applicationContext gracefully.
     */
    @PreDestroy
    public void shutdown() {
        System.out.println("Deleting downloaded templates...");
        // TODO actual deletion of PDFs
    }

//    // Setter injection
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
}
