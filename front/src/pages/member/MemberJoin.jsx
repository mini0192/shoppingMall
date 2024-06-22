import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Url from "../../config/backendConnect";
import axios from "axios";

function MemberJoin() {

    const navigate = useNavigate()

    const [name, setName] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const inputName = text => {
        setName(text.target.value)
    }

    const inputPassword = text => {
        setPassword(text.target.value)
    }
    
    const inputEmail = text => {
        setEmail(text.target.value)
    }

    const submitButton = () => {
        const data = {
            name: name,
            username: email,
            password: password
        }

        const config = {"Content-Type": 'application/json'};

        axios.post(`${Url}/member/add`, data, config)
        .then(res => {
            navigate("/")
        })
        .catch(err => {
            alert(err.response.data.error[0])
        })
    }

    return (
        <section>
            <div className="form-floating mb-3">
                <input type="text" className="form-control" placeholder="이름" onChange={ inputName }/>
                <label htmlFor="floatingInput">이름</label>
            </div>
            <div className="form-floating mb-3">
                <input type="email" className="form-control" placeholder="name@example.com" onChange={ inputEmail }/>
                <label htmlFor="floatingInput">이메일</label>
            </div>
            <div className="form-floating">
                <input type="password" className="form-control" placeholder="Password" onChange={ inputPassword }/>
                <label htmlFor="floatingPassword">비밀번호</label>
            </div>
            
            <br/>
            <div className="d-grid gap-2 col-6 mx-auto">
                <button className="btn btn-info" type="button" onClick={ submitButton }>회원가입</button>
            </div>
        </section>
    )
}

export default MemberJoin;