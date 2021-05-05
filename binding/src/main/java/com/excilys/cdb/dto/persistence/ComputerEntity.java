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
public class ComputerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private CompanyEntity companyEntity;

	private ComputerEntity() {
	}

	public ComputerEntity(Long id, String name, LocalDate introduced, LocalDate discontinued,
			CompanyEntity companyEntity) {
		this();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyEntity = companyEntity;
	}

	private ComputerEntity(ComputerEntityBuilder computerEntityBuilder) {
		this.id = computerEntityBuilder.id;
		this.name = computerEntityBuilder.name;
		this.introduced = computerEntityBuilder.introduced;
		this.discontinued = computerEntityBuilder.discontinued;
		this.companyEntity = computerEntityBuilder.companyEntity;
	}

	public static class ComputerEntityBuilder {
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private CompanyEntity companyEntity;

		public ComputerEntityBuilder(Long id) {
			this.id = id;
		}

		public ComputerEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerEntityBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerEntityBuilder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerEntityBuilder company(CompanyEntity companyEntity) {
			this.companyEntity = companyEntity;
			return this;
		}

		public ComputerEntity build() {
			ComputerEntity computerEntity = new ComputerEntity(this);
			return computerEntity;
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

	public CompanyEntity getCompanyEntity() {
		return companyEntity;
	}

	public void setCompanyEntity(CompanyEntity companyEntity) {
		this.companyEntity = companyEntity;
	}

	public Long getCompanyId() {

		if (companyEntity != null && companyEntity.getIdCompany() != null) {
			return companyEntity.getIdCompany();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyEntity == null) ? 0 : companyEntity.hashCode());
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
		ComputerEntity other = (ComputerEntity) obj;
		if (companyEntity == null) {
			if (other.companyEntity != null) {
				return false;
			}
		} else if (!companyEntity.equals(other.companyEntity)) {
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
		return "ComputerEntity [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + companyEntity + "]";
	}
}
