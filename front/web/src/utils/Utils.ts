export const scrollToTop = () => {
    window.scrollTo({
        top: 0,
        behavior: 'smooth' // 'auto' para salto instantâneo
    });
};