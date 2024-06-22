function PageNumberConfig(data, c, option, apiFunc) {
    const pageNumber = data.pageable?.pageNumber + 1;
    const totalPage = data?.totalPages

    function func(tester, number) {
        if(tester) {
            return (
                <button type="button" className={`btn ${setClass(number)}`} onClick={() => {apiFunc(c, number, option)}}>
                    {number}
                </button>
            )
        }
    }

    const setClass = (num) => {
        if(pageNumber === num) {
            return "btn-info"
        }
        return "btn-outline-info"
    }

    return (
        <div>
            <div className="btn-group marginTopObj" role="group" aria-label="Basic example">
                { func((pageNumber - 2) > 0, pageNumber - 2) }
                { func((pageNumber - 1) > 0, pageNumber - 1) }
                { func(true, pageNumber) }
                { func((pageNumber + 1) <= totalPage, pageNumber + 1) }
                { func((pageNumber + 2) <= totalPage, pageNumber + 2) }
            </div>
        </div>
    )
}

export default PageNumberConfig