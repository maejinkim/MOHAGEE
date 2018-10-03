from gensim.models import Word2Vec
import numpy as np
from gensim.models import doc2vec, word2vec
from konlpy.tag import Okt
from konlpy.utils import pprint
import codecs
import gensim
from gensim import corpora, models
from gensim.models import CoherenceModel, KeyedVectors
import os
from collections import Counter
import random; random.seed(0)
import webbrowser
from konlpy.tag import Mecab
import pytagcloud
import pyLDAvis
import pyLDAvis.gensim
import matplotlib.pyplot as plt
import warnings
from soynlp.word import WordExtractor
from soykeyword.lasso import LassoKeywordExtractor
from soykeyword.proportion import CorpusbasedKeywordExtractor
import soykeyword
from gensim.models.keyedvectors import KeyedVectors
from os import listdir
from os.path import isfile, join

warnings.filterwarnings(action='ignore')

files = [f for f in listdir('./data') if isfile(join('./data', f))]


okt = Okt()


def read_data(filename):
    with open(filename, 'rb',) as f:
        data = [line.decode("cp949", errors='ignore') for line in f.read().splitlines()]
    return data

def tokenize(doc):
  # norm, stem은 optional
  return ["".join(t) for t in okt.nouns(doc)]

train_doc = []


print("apply Word2vec model...")
embedding_model = gensim.models.Word2Vec.load('word2vec.model')

print("start LDA...")
for review_name in files:
    filters = ["재미", "꿀맛", "아늑", "핫", "데이트", "가족", "나들이"]
    train_doc = read_data('data/'+ review_name)
    noun = [tokenize(row) for row in train_doc]
    dictionary = corpora.Dictionary(noun)
    corpus = [dictionary.doc2bow(text) for text in noun]

    ldamodel = gensim.models.ldamodel.LdaModel(corpus=corpus,
                                               num_topics=3,
                                               id2word=dictionary,
                                               random_state=100,
                                               passes=30,
                                               alpha='auto')


    temps = ldamodel.print_topics(num_topics=20, num_words=5)
    #embedding_model = gensim.models.Word2Vec.load('./all_vectorize.bin')
    #print(embedding_model.most_similar("재미"))
    new_words = []
    sums = []
    for filt in filters:
        sum = 0
        for temp in temps:
            k = temp[1]
            k = k.split('+')
            for i in k:
                i = i.split('*')
                temp_noun = i[1].split('"')
                new_words.append(temp_noun[1])
    #필터 돌리기
    for filt in filters:
        sum = 0
        #
        for temp in temps:
            k = temp[1]
            k = k.split('+')
            for i in k:
                i = i.split('*')
                i[0] = float(i[0])
                for word in new_words:
                    sum = sum + float(i[0]*embedding_model.similarity(filt, word))
                sums.append(sum)
                break
            break
    print(review_name, filters[sums.index(max(sums))])
    temp_index = sums.index(max(sums))

    filters.pop(temp_index)
    sums.pop(temp_index)

    print(review_name, filters[sums.index(max(sums))])
    sums.clear()
    new_words.clear()
    noun.clear()
#embedding_model = gensim.models.KeyedVectors.load_word2vec_format('./ko.bin', binary=True)

#embedding_model = Word2Vec(noun, size=100, window=2, min_count=50, workers=4, iter=100,sg=1)

#doc_lda = ldamodel[corpus]


# Compute Perplexity
#print('\nPerplexity: ', ldamodel.log_perplexity(corpus)) # a measure of how good the model is. lower the better.

# Compute Coherence Score
#coherence_model_lda = CoherenceModel(model=ldamodel, texts=train_docs, dictionary=dictionary, coherence='c_v')
#coherence_lda = coherence_model_lda.get_coherence()
#print('\nCoherence Score: ', coherence_lda)


# Visualize the topics
#pyLDAvis.enable_notebook()
#vis = pyLDAvis.gensim.prepare(ldamodel, corpus, dictionary)






"""

def read_data(filename):
    with open(filename, 'r') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
    return data

def tokenize(doc):
  # norm, stem은 optional
  return ['/'.join(t) for t in okt.pos(doc, norm=True, stem=True)]


okt = Okt()
txtfile = read_data('data/ratings_train.txt')
test_data = read_data('data/ratings_test.txt')

train_docs = [(tokenize(row[1]), row[2]) for row in txtfile[1:]]
test_docs = [(tokenize(row[1]), row[2]) for row in test_data[1:]]

TaggedDocument = namedtuple('TaggedDocument', 'words tags')
tagged_train_docs = [TaggedDocument(d, [c]) for d, c in train_docs]
tagged_test_docs = [TaggedDocument(d, [c]) for d, c in test_docs]

#model generation
doc_vectorizer = doc2vec.Doc2Vec(
    size=300,
    alpha=0.025,
    min_alpha=0.025,
    seed=1234,
    window=8,
    negative=10,)
doc_vectorizer.build_vocab(tagged_train_docs)


def train():

    # Train document vectors!
    for epoch in range(10):
        doc_vectorizer.train(tagged_train_docs, total_examples=doc_vectorizer.corpus_count, epochs=doc_vectorizer.iter)
        doc_vectorizer.alpha -= 0.002  # decrease the learning rate
        doc_vectorizer.min_alpha = doc_vectorizer.alpha  # fix the learning rate, no decay

    #To save
    doc_vectorizer.save('model/doc2vec.model')

def test():
    train_x = [doc_vectorizer.infer_vector(doc.words) for doc in tagged_train_docs]
    train_y = [doc.tags[0] for doc in tagged_train_docs]
    test_x = [doc_vectorizer.infer_vector(doc.words) for doc in tagged_test_docs]
    test_y = [doc.tags[0] for doc in tagged_test_docs]
    doc_vectorizer.


#kkma = Kkma()
#pprint(kkma.pos(u'질문이나 건의사항은 깃 이슈 트래커에 남겨주세요.'))

"""
#embedding_model = Word2Vec(size=100, window=2, min_count=50, workers=4, iter=100, sg=1)
