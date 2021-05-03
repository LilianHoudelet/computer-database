package com.excilys.cdb.dto.persistence;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerDTOPersistence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private CompanyDTOPersistence companyDTOPersistence;

	private ComputerDTOPersistence() {
	}

	public ComputerDTOPersistence(Long id, String name, LocalDate introduced, LocalDate discontinued,
			CompanyDTOPersistence companyDTOPersistence) {
		this();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyDTOPersistence = companyDTOPersistence;
	}

	private ComputerDTOPersistence(ComputerDTOPersistenceBuilder computerDTOPersistanceBuilder) {
		this.id = computerDTOPersistanceBuilder.id;
		this.name = computerDTOPersistanceBuilder.name;
		this.introduced = computerDTOPersistanceBuilder.introduced;
		this.discontinued = computerDTOPersistanceBuilder.discontinued;
		this.companyDTOPersistence = computerDTOPersistanceBuilder.companyDTOPersistance;
	}

	public static class ComputerDTOPersistenceBuilder {
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private CompanyDTOPersistence companyDTOPersistance;

		public ComputerDTOPersistenceBuilder(Long id) {
			this.id = id;
		}

		public ComputerDTOPersistenceBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerDTOPersistenceBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDTOPersistenceBuilder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTOPersistenceBuilder company(CompanyDTOPersistence companyDTOPersistance) {
			this.companyDTOPersistance = companyDTOPersistance;
			return this;
		}

		public ComputerDTOPersistence build() {
			ComputerDTOPersistence computerDTOPersistance = new ComputerDTOPersistence(this);
			return computerDTOPersistance;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTOPersistence getCompanyDTOPersistance() {
		return companyDTOPersistence;
	}

	public void setCompanyDTOPersistance(CompanyDTOPersistence companyDTOPersistance) {
		this.companyDTOPersistence = companyDTOPersistance;
	}

	public Long getCompanyId() {

		if (companyDTOPersistence != null && companyDTOPersistence.getIdCompany() != null) {
			return companyDTOPersistence.getIdCompany();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyDTOPersistence == null) ? 0 : companyDTOPersistence.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComputerDTOPersistence other = (ComputerDTOPersistence) obj;
		if (companyDTOPersistence == null) {
			if (other.companyDTOPersistence != null) {
				return false;
			}
		} else if (!companyDTOPersistence.equals(other.companyDTOPersistence)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ComputerDTOPersistance [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + companyDTOPersistence + "]";
	}
}
