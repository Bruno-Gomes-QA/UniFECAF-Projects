fn fatorial(n: u32) -> u32 {
    if n == 0 {1} else {n * fatorial(n)}
}

fn main() {
    println!("Fatorial de 5: {}", fatorial(5));
}
