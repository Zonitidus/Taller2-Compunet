package co.edu.icesi.dev.uccareapp.transport.model.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the salespersonquotahistory database table.
 *
 */
@Entity
@NamedQuery(name = "Salespersonquotahistory.findAll", query = "SELECT s FROM Salespersonquotahistory s")
public class Salespersonquotahistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SALESTERRITORYQUOTAHISTORY_ID_GENERATOR", allocationSize = 1, sequenceName = "SALESTERRITORYQUOTAHISTORY_SEC")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALESTERRITORYQUOTAHISTORY_ID_GENERATOR")
	private Integer id;

	@PastOrPresent(message = "Date can't be >= today")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date modifieddate;

	private Integer rowguid;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than 0")
	private BigDecimal salesquota;

	// bi-directional many-to-one association to Salesperson
	@ManyToOne
	@JoinColumn(name = "businessentityid", insertable = true, updatable = true)
	@JsonIgnoreProperties({"salespersonquotahistories","salesorderheaders","salesterritory","salesterritoryhistories"})
	private Salesperson salesperson;

	public Salespersonquotahistory() {
	}

	public Integer getId() {
		return this.id;
	}

	public Date getModifieddate() {
		return this.modifieddate;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public Salesperson getSalesperson() {
		return this.salesperson;
	}

	public BigDecimal getSalesquota() {
		return this.salesquota;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

	public void setSalesperson(Salesperson salesperson) {
		this.salesperson = salesperson;
	}

	public void setSalesquota(BigDecimal salesquota) {
		this.salesquota = salesquota;
	}

}