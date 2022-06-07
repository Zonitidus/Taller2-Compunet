package co.edu.icesi.dev.uccareapp.transport.model.sales;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the salesterritoryhistory database table.
 *
 */
@Entity
@NamedQuery(name = "Salesterritoryhistory.findAll", query = "SELECT s FROM Salesterritoryhistory s")
public class Salesterritoryhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SALESTERRITORYHISTORY_ID_GENERATOR", allocationSize = 1, sequenceName = "SALESTERRITORYHISTORY_SEC")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALESTERRITORYHISTORY_ID_GENERATOR")
	private Integer id;

	@NotNull
	@PastOrPresent(message= "End date must be less or equals to the current date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enddate;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifieddate;

	private Integer rowguid;

	// bi-directional many-to-one association to Salesperson
	@ManyToOne
	@JoinColumn(name = "businessentityid", insertable = true, updatable = true)
	@JsonIgnoreProperties(value = {"salespersonquotahistories","salesorderheaders","salesterritory","salesterritoryhistories"}, allowSetters = true)
	private Salesperson salesperson;

	// bi-directional many-to-one association to Salesterritory
	@ManyToOne
	@JoinColumn(name = "territoryid")
	@JsonIgnoreProperties(value = {"customers","salesorderheaders","salespersons","salesterritoryhistories", "businessentities"}, allowSetters = true)
	private Salesterritory salesterritory;

	public Salesterritoryhistory() {
	}

	public Date getEnddate() {
		return this.enddate;
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

	public Salesterritory getSalesterritory() {
		return this.salesterritory;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public void setSalesterritory(Salesterritory salesterritory) {
		this.salesterritory = salesterritory;
	}

}