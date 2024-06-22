import ReplaceMouny from "../../../config/ReplaceMouny";

function Content(props) {
    const data = props.data
    return (
        <div className="card paddingObj" style={{textAlign: "left", height: "100%"}}>
            <div className="card-body">
                <h3 className="card-title">{ data.name }</h3>
                <p className="card-text">
                    { ReplaceMouny(data.price) }
                </p>
            </div>


            <div className="accordion" id="content">
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            상세 내용
                        </button>
                    </h2>
                    <div id="collapseThree" className="accordion-collapse collapse" data-bs-parent="#content">
                        <div className="accordion-body">
                            { data.content }
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default Content;