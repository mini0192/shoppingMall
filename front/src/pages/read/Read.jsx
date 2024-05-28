import { Route, Routes } from "react-router-dom";
import ReadList from "./ReadList";
import ReadId from "./ReadId";

function Read() {
    return (
        <div>
            <Routes>
                <Route path="/" element={ <ReadList/> }></Route>
                <Route path=":id" element={ <ReadId/> }></Route>
            </Routes>
        </div>
    )
}

export default Read;