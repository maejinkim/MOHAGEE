import gensim

model = gensim.models.KeyedVectors.load_word2vec_format('./ko.bin', binary='True')
sentence = ["아늑함", "재미있음", "재미없음"]
vectors = [model[w] for w in sentence]

print(vectors)