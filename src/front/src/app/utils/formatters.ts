export const formatarTelefone = (valor: number) => {
	const v = valor.toString();
	return `(${v.substring(0, 2)}) ${v.substring(2, 7)}-${v.substring(7, 11)}`;
};

// DEVE ESTAR NO FORMATO: YYYY-MM-DD
export const formatarData = (valor: string) => {
	return `${valor.substring(8, 10)}/${valor.substring(5, 7)}/${valor.substring(0, 4)}`;
};
