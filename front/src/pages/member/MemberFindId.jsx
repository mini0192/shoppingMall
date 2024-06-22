import { Link } from "react-router-dom";

function MemberFindId() {
    return (
        <section>
            <div className="form-floating mb-3">
                <input type="text" className="form-control" placeholder="이름"/>
                <label HtmlFor="floatingInput">이름</label>
            </div>
            <div className="form-floating">
                <input type="password" className="form-control" placeholder="name@example.com"/>
                <label HtmlFor="floatingPassword">이메일</label>
            </div>

            <br/>
            <div className="d-grid gap-2 col-6 mx-auto">
                <button className="btn btn-info" type="button">비밀번호 찾기</button>
            </div>

            
            <div className="container text-center paddingObj" style={{marginTop: "50px"}}>
                <div className="row">
                    <div className="col">
                        <Link to="/member/login" className="linkObj"><p>로그인으로 돌아가기</p></Link>
                    </div>
                </div>
            </div>
            
        </section>
    )
}

export default MemberFindId;