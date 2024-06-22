import { Route, Routes } from "react-router-dom";
import MemberLogin from "./MemberLogin";
import MemberJoin from "./MemberJoin";
import MemberFindId from "./MemberFindId";

function MemberForm() {
    return (
        <section>
            <div className="card paddingObj shadow-sm p-3 mb-5 bg-body-tertiary rounded" style={{width: "500px", height: "550px", margin: "0 auto"}}>
            <br/>
            <h1 className="pointColorObj" style={{textAlign: "center", margin: "30px"}}>MINI</h1>
            <Routes>
                <Route path="/login" element={<MemberLogin/>}/>
                <Route path="/join" element={<MemberJoin/>}/>
                <Route path="/find" element={<MemberFindId/>}/>
            </Routes>
            </div>
        </section>
    )
}

export default MemberForm;