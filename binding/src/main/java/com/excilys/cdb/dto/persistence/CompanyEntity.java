package com.excilys.cdb.dto.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class CompanyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idCompany;
	private String name;

	private CompanyEntity() {
		
	}

	private CompanyEntity(CompanyEntityBuilder computerBuilder) {
		this.idCompany = computerBuilder.idCompany;
		this.name = computerBuilder.name;
	}

	public static class CompanyEntityBuilder {
		Long idCompany;
		String name;
		
		public CompanyEntityBuilder(Long idCompany) {
			this.idCompany = idCompany;
		}

		public CompanyEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public CompanyEntity build() {
			CompanyEntity company = new CompanyEntity(this);
			return company;
		}

	}

	public Long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyEntity [id=" + idCompany + ", name=" + name + "]";
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
		CompanyEntity other = (CompanyEntity) obj;
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
