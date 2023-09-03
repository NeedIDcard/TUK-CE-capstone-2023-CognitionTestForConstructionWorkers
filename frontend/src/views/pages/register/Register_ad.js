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
const Register_ad = () => {
  const dispatch = useDispatch()
  const token = sessionStorage.getItem('token')
  const [ID, setID] = useState('')
  const [Name, setName] = useState('')
  const [Password, setPassword] = useState('')
  const [Age, setAge] = useState('')
  const [Field, setField] = useState('')
  const [loading, setLoading] = useState(false)
  const [role, setRole] = useState('')
  const [Position, setPosition] = useState('')
  const [Repeatpw, setRepeatpw] = useState('')

  const UserFunc = (e) => {
    e.preventDefault()

    const Admin = {
      name: Name,
      username: ID,
      password: Password,
      fieldId: Field,
      //position: Position,
      //role: role,
    }
    const Subjects = `name=${Name}&username=${ID}&password=${Password}&age=${Age}`
    if (Password === Repeatpw) {
      axios.defaults.headers.common['Authorization'] = `${token}`
      axios
        .post('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/super/admin', Admin, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          },
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        })
        .then((response) => {
          console.log(response.data)
          window.location.href = '#/admins'
        })
        .catch((error) => {
          alert(error.message)
        })
    } else {
      alert('패스워드가 다릅니다.')
    }
  }
  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm onSubmit={UserFunc}>
                  <h2>Admin Create</h2>
                  <p className="text-medium-emphasis">Create Admin account</p>
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
                      value={Repeatpw}
                      onChange={(e) => setRepeatpw(e.target.value)}
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
export default Register_ad
