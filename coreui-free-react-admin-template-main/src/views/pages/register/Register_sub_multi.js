import { React, useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'
import qs from 'qs'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import axios from 'axios'
const Register = () => {
  const dispatch = useDispatch()
  const token = sessionStorage.getItem('token')
  const [ID, setID] = useState('')
  const [Name, setName] = useState('')
  const [Password, setPassword] = useState('')
  const [Age, setAge] = useState('')
  const [Field, setField] = useState('')
  const [loading, setLoading] = useState(false)
  const [msg, setMsg] = useState('')
  const [Carrer, setCarrer] = useState('')
  useEffect(() => {
    /*...*/
  }, [msg])

  const UserFunc = (e) => {
    e.preventDefault()
    const Subjects = [
      {
        name: Name,
        username: ID,
        password: Password,
        age: Age,
        career: Carrer,
      },
      {
        name: 'test3',
        username: 'test3',
        password: 'test',
        age: '1',
        career: '1',
      },
    ]
    axios.defaults.headers.common['Authorization'] = `${token}`
    axios
      .post(
        'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subject',
        {
          mode: 'multi',
          subjects: Subjects,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          },
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
      )
      .then((response) => {
        console.log(response.data)
      })
      .catch((error) => {
        alert(error.message)
      })
  }
  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm onSubmit={UserFunc}>
                  <h2>Subject Create</h2>
                  <p className="text-medium-emphasis">Create subject account</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Username"
                      autoComplete="Username"
                      value={Name}
                      onChange={(e) => setName(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="ID"
                      autoComplete="ID"
                      value={ID}
                      onChange={(e) => setID(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autoComplete="new-password"
                      value={Password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Repeat password"
                      autoComplete="new-password"
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Age"
                      autoComplete="Age"
                      value={Age}
                      onChange={(e) => setAge(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Field"
                      autoComplete="Field"
                      value={Field}
                      onChange={(e) => setField(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Carrer"
                      autoComplete="Carrer"
                      value={Carrer}
                      onChange={(e) => setCarrer(e.target.value)}
                    />
                  </CInputGroup>
                  <div className="d-grid">
                    <CButton type="submit" color="success">
                      Create Account
                    </CButton>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}
export default Register
