function ReplaceMouny(number) {
    return `${number?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}원`
}

export default ReplaceMouny;