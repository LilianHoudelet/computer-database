package com.excilys.cdb.dto.rest;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ComputerDTORest implements Serializable {

	private static final long serialVersionUID = 4599355415710447147L;
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private CompanyDTORest company;

	private ComputerDTORest(ComputerDTORestBuilder computerBuilder) {
		this.id = computerBuilder.id;
		this.name = computerBuilder.name;
		this.introduced = computerBuilder.introduced;
		this.discontinued = computerBuilder.discontinued;
		this.company = computerBuilder.company;
	}

	private ComputerDTORest() {

	}

	public static class ComputerDTORestBuilder {
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private CompanyDTORest company;

		public ComputerDTORestBuilder(Long id) {
			this.id = id;
		}

		public ComputerDTORestBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerDTORestBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDTORestBuilder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTORestBuilder company(CompanyDTORest companyDTORest) {
			this.company = companyDTORest;
			return this;
		}

		public ComputerDTORest build() {
			ComputerDTORest computer = new ComputerDTORest(this);
			return computer;
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
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTORest getCompanyDTORest() {
		return company;
	}

	public void setCompanyDTORest(CompanyDTORest companyDTORest) {
		this.company = companyDTORest;
	}

//	public Long getCompanyId() {
//
//		if (companyDTORest != null && companyDTORest.getId() != null) {
//			return companyDTORest.getId();
//		}
//		return null;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
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
		ComputerDTORest other = (ComputerDTORest) obj;
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
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
		return "ComputerDTORest [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]";
	}

}
