package com.excilys.cdb.dto.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.excilys.cdb.model.Computer;

@Entity
@Table(name = "company")
public class CompanyDTOPersistence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idCompany;
	private String name;

	@OneToMany(targetEntity = ComputerDTOPersistence.class, mappedBy = "companyDTOPersistance")
	private List<Computer> computers = new ArrayList<>();

	private CompanyDTOPersistence() {
	}

	private CompanyDTOPersistence(CompanyDTOPersistanceBuilder computerBuilder) {
		this.idCompany = computerBuilder.idCompany;
		this.name = computerBuilder.name;

	}

	public static class CompanyDTOPersistanceBuilder {

		Long idCompany;
		String name;

		public CompanyDTOPersistanceBuilder(Long idCompany) {
			this.idCompany = idCompany;
		}

		public CompanyDTOPersistanceBuilder name(String name) {
			this.name = name;
			return this;
		}

		public CompanyDTOPersistence build() {
			CompanyDTOPersistence company = new CompanyDTOPersistence(this);
			return company;
		}

	}

	/**
	 * @return any id Value
	 */
	public Long getIdCompany() {
		return idCompany;
	}

	/**
	 * @param idCompany any Long value
	 */
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}

	/**
	 * @return any String value
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name any String Value
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyDTOPersistance [id=" + idCompany + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCompany == null) ? 0 : idCompany.hashCode());
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
		CompanyDTOPersistence other = (CompanyDTOPersistence) obj;
		if (idCompany == null) {
			if (other.idCompany != null) {
				return false;
			}
		} else if (!idCompany.equals(other.idCompany)) {
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
}
