use rand::Rng;

fn main() {
    // 1) Gera vetor com 20 inteiros aleatórios entre 0 e 100
    let mut rng = rand::thread_rng();
    let base: Vec<i32> = (0..20).map(|_| rng.gen_range(0..=100)).collect();

    println!("Vetor original: {:?}", base);

    // 2) Bubble Sort (in-place)
    let mut v_bubble = base.clone();
    bubble_sort(&mut v_bubble);     
    println!("Bubble Sort:    {:?}", v_bubble);

    // 3) Quick Sort (in-place)
    let mut v_quick = base.clone();
    quick_sort(&mut v_quick);
    println!("Quick Sort:     {:?}", v_quick);

    // 4) Merge Sort (gera novo vetor)
    let v_merge = merge_sort(&base);
    println!("Merge Sort:     {:?}", v_merge);
}

// ----------------- Bubble Sort -----------------
fn bubble_sort(v: &mut [i32]) {
    let n = v.len();
    if n < 2 { return; }
    for i in 0..n {
        let mut trocou = false;
        for j in 0..(n - 1 - i) {
            if v[j] > v[j + 1] {
                v.swap(j, j + 1);
                trocou = true;
            }
        }
        if !trocou { break; }
    }
}

// ----------------- Quick Sort ------------------
fn quick_sort(v: &mut [i32]) {
    if v.len() <= 1 { return; }
    quick_sort_rec(v, 0, (v.len() - 1) as isize);
}

fn quick_sort_rec(v: &mut [i32], lo: isize, hi: isize) {
    if lo >= hi { return; }
    let p = particiona(v, lo, hi);
    quick_sort_rec(v, lo, p - 1);
    quick_sort_rec(v, p + 1, hi);
}

fn particiona(v: &mut [i32], lo: isize, hi: isize) -> isize {
    let pivot = v[hi as usize];
    let mut i = lo - 1;
    for j in lo..hi {
        if v[j as usize] <= pivot {
            i += 1;
            v.swap(i as usize, j as usize);
        }
    }
    v.swap((i + 1) as usize, hi as usize);
    i + 1
}

// ----------------- Merge Sort ------------------
fn merge_sort(v: &[i32]) -> Vec<i32> {
    if v.len() <= 1 {
        return v.to_vec();
    }
    let mid = v.len() / 2;
    let esquerda = merge_sort(&v[..mid]);
    let direita  = merge_sort(&v[mid..]);
    merge(&esquerda, &direita)
}

fn merge(a: &[i32], b: &[i32]) -> Vec<i32> {
    let mut i = 0;
    let mut j = 0;
    let mut r = Vec::with_capacity(a.len() + b.len());

    while i < a.len() && j < b.len() {
        if a[i] <= b[j] {
            r.push(a[i]); i += 1;
        } else {
            r.push(b[j]); j += 1;
        }
    }
    if i < a.len() { r.extend_from_slice(&a[i..]); }
    if j < b.len() { r.extend_from_slice(&b[j..]); }
    r
}
